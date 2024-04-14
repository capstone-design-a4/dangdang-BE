package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

}
