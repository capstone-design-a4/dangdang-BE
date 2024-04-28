package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.DrinkDto;
import com.capstone.dangdang.dto.entity.DrinkRecordDto;
import com.capstone.dangdang.entity.CafeEntity;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.enums.DrinkSortCond;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.BookmarkRepository;
import com.capstone.dangdang.repository.CafeRepository;
import com.capstone.dangdang.repository.DrinkRepository;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    @Value("${file:}")
    private String rootFilePath;

    public List<DrinkDto> getRecommendDrinkList() {

        List<DrinkEntity> drinkEntities = drinkRepository.findTop10ByBookmarkCount();
        return drinkEntities.stream().map(drink -> new DrinkDto(drink)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<DrinkDto> searchDrink(CafeName cafeName, String searchCond, DrinkSortCond sortCond, int pageNo, Authentication authentication) {
        Pageable pageable;
        switch (sortCond) {
            case SUGAR:
                pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.ASC, "sugar"));
                break;
            case CALORIES:
                pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.ASC, "calorie"));
                break;
            default:
                throw new IllegalArgumentException("Invalid sort condition: " + sortCond);
        }

        Page<DrinkEntity> drinksPage = drinkRepository.findByNameContainingAndCafe_Name(searchCond, cafeName, pageable);

        List<DrinkDto> drinkDtoList = drinksPage.getContent().stream()
                .map(DrinkDto::new)
                .collect(Collectors.toList());

        List<DrinkDto> updatedDrinkDtoList = fetchBookmarkToDrink(drinkDtoList, authentication);

        return new PageImpl<>(updatedDrinkDtoList, pageable, drinksPage.getTotalElements());
    }


    public List<DrinkDto> fetchBookmarkToDrink(List<DrinkDto> drinkList, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        List<Long> drinkIds = drinkList.stream().map(DrinkDto::getId).collect(Collectors.toList());
        List<Long> bookmarkedDrinkIds = bookmarkRepository.findBookmarkedDrinkByMemberAndDrink(memberEntity, drinkIds);
        for (DrinkDto drinkDto : drinkList) {
            if (bookmarkedDrinkIds.contains(drinkDto.getId())) {
                drinkDto.setBookmarked(true);
            }
        }

        return drinkList;
    }

    public List<DrinkDto> getDrinkList(CafeName cafeName) {
        List<DrinkEntity> drinkEntityList = drinkRepository.findByCafe_Name(cafeName);

        List<DrinkDto> drinkDtoList = drinkEntityList.stream()
                .map(entity ->
                        DrinkDto.builder()
                        .id(entity.getId())
                        .sugar(entity.getSugar())
                        .name(entity.getName())
                        .cafeName(entity.getCafe().getName())
                        .calorie(entity.getCalorie())
                        .imageUrl(entity.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return drinkDtoList;
    }

    public Resource getDrinkImage(Long drinkId) {
        DrinkEntity drinkEntity = drinkRepository.findById(drinkId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        String cafeName = drinkEntity.getCafe().getName().name();
        try {
            Path dirPath = Paths.get(rootFilePath, cafeName, drinkEntity.getImageUrl()).toAbsolutePath();
            Optional<Path> imagePath = Files.walk(dirPath)
                    .filter(path -> path.getFileName().toString().matches(drinkEntity.getImageUrl()))
                    .findFirst();

            if (imagePath.isPresent()) {
                Resource imageResource = new UrlResource(imagePath.get().toUri());
                if (imageResource.exists()) {
                    return imageResource;
                } else {
                    throw new CustomException(ErrorCode.FILE_NOT_FOUND);
                }
            } else {
                throw new CustomException(ErrorCode.FILE_NOT_FOUND);
            }

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
