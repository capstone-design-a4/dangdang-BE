package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.DrinkRecordDto;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.DrinkRecordEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.DrinkRecordRepository;
import com.capstone.dangdang.repository.DrinkRepository;
import com.capstone.dangdang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrinkRecordService {

    private final DrinkRecordRepository drinkRecordRepository;
    private final MemberRepository memberRepository;
    private final DrinkRepository drinkRepository;

    public void addDrinkRecord(Long drinkId, Authentication authentication) {
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        DrinkEntity drinkEntity = drinkRepository.findById(drinkId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        DrinkRecordEntity drinkRecordEntity = DrinkRecordEntity.builder()
                .drink(drinkEntity)
                .member(memberEntity)
                .build();

        drinkRecordRepository.save(drinkRecordEntity);
    }



    public List<DrinkRecordDto> getTodayDrinkRecords(Authentication authentication) {

        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        return drinkRecordRepository.findAllByCreateDateIsTodayAndMemberId(LocalDate.now(), memberEntity.getId()).stream()
                .map(DrinkRecordDto::new)
                .collect(Collectors.toList());
    }

    public void deleteDrinkRecord(Long drinkRecordId, Authentication authentication) {
        DrinkRecordEntity drinkRecordEntity = drinkRecordRepository.findById(drinkRecordId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        MemberEntity memberEntity = memberRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        if(!drinkRecordEntity.getMember().equals(memberEntity))
            throw new CustomException(ErrorCode.BAD_REQUEST);

        drinkRecordRepository.delete(drinkRecordEntity);
    }


}
