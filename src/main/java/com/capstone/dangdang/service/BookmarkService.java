package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.DrinkDto;
import com.capstone.dangdang.dto.entity.DrinkRecordDto;
import com.capstone.dangdang.entity.BookmarkEntity;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.BookmarkRepository;
import com.capstone.dangdang.repository.DrinkRepository;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final DrinkRepository drinkRepository;



    @Transactional(readOnly = true)
    public List<DrinkDto> getBookmarkedDrinkList(Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        List<DrinkEntity> bookmarkedDrinks = drinkRepository.findDrinksByMember(memberEntity);

        return bookmarkedDrinks.stream()
                .map(drink -> new DrinkDto(
                        drink.getId(),
                        drink.getName(),
                        drink.getCafe().getName(),
                        drink.getSugar(),
                        drink.getCalorie(),
                        drink.getImageUrl(),
                        true))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<DrinkRecordDto> fetchBookmarkToRecord(List<DrinkRecordDto> drinkRecordList, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        List<Long> drinkIds = drinkRecordList.stream().map(DrinkRecordDto::getId).collect(Collectors.toList());
        List<Long> bookmarkedDrinkIds = bookmarkRepository.findBookmarkedDrinkByMemberAndDrink(memberEntity, drinkIds);
        for (DrinkRecordDto drinkRecordDto : drinkRecordList) {
            if (bookmarkedDrinkIds.contains(drinkRecordDto.getId())) {
                DrinkDto drink = drinkRecordDto.getDrink();
                drink.setBookmarked(true);
            }
        }
        return drinkRecordList;
    }

    @Transactional
    public void add(Long drinkId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        DrinkEntity drinkEntity = drinkRepository.findById(drinkId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if (bookmarkRepository.existsByMember_IdAndDrink_Id(memberEntity.getId(), drinkId)) {
            throw new CustomException(ErrorCode.ALREADY_BOOKMARKED_MENU);
        }

        BookmarkEntity bookmarkEntity = BookmarkEntity.builder()
                .drink(drinkEntity)
                .cafe(drinkEntity.getCafe())
                .member(memberEntity)
                .build();

        bookmarkRepository.save(bookmarkEntity);
    }

    public void cancel(Long drinkId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        BookmarkEntity bookmarkEntity = bookmarkRepository.findByMember_IdAndDrink_Id(memberEntity.getId(), drinkId)
                .orElseThrow(() -> new CustomException(ErrorCode.ALREADY_BOOKMARKED_MENU));

        bookmarkRepository.delete(bookmarkEntity);
    }
}
