package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.DrinkRecordEntity;
import com.capstone.dangdang.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface DrinkRecordRepository extends JpaRepository<DrinkRecordEntity, Long> {

    @Query("SELECT d FROM DrinkRecordEntity d WHERE DATE(d.createDate) = :today AND d.member.id = :memberId")
    List<DrinkRecordEntity> findAllByCreateDateIsTodayAndMemberId(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today, @Param("memberId") Long memberId);
}
