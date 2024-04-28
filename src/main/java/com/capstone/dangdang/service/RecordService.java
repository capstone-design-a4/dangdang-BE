package com.capstone.dangdang.service;

import com.capstone.dangdang.dto.entity.RecordDto;
import com.capstone.dangdang.entity.RecordEntity;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.capstone.dangdang.util.StatCalculatorUtils.*;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional(readOnly = true)
    public RecordDto getMyDayRecord(Authentication auth) {
        RecordDto recordDto = new RecordDto(recordRepository.findByMember_LoginId(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST)));
        getDayStat(recordDto);

        return recordDto;
    }

    @Transactional(readOnly = true)
    public RecordDto getMyWeekRecord(Authentication auth) {
        RecordDto recordDto = new RecordDto(recordRepository.findByMember_LoginId(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST)));
        getWeekStat(recordDto);

        return recordDto;
    }

    @Transactional(readOnly = true)
    public RecordDto getMyMonthRecord(Authentication auth) {
        RecordDto recordDto = new RecordDto(recordRepository.findByMember_LoginId(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST)));
        getMonthStat(recordDto);

        return recordDto;
    }
    @Transactional
    public RecordDto setMyGoal(int goal, Authentication auth) {

        if(goal <= 0) {
            throw new CustomException(ErrorCode.NEGATIVE_GOAL);
        }

        RecordEntity recordEntity = recordRepository.findByMember_LoginId(auth.getName()).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        recordEntity.setGoal(goal);
        return new RecordDto(recordRepository.save(recordEntity));
    }


}