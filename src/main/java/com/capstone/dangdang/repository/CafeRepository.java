package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.CafeEntity;
import com.capstone.dangdang.enums.CafeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<CafeEntity, Long> {

    Optional<CafeEntity> findByName(CafeName cafeName);
}
