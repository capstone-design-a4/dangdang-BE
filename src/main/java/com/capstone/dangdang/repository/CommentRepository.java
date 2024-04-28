package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


}
