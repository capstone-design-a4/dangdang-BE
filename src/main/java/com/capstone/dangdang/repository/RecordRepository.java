package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    Optional<RecordEntity> findByMember_LoginId(String loginId);
}