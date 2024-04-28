package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.BoardEntity;
import com.capstone.dangdang.enums.CafeName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    @Query("SELECT b FROM BoardEntity b WHERE " + "(b.title LIKE %:searchContextCond% OR b.content LIKE %:searchContextCond%) " +
            "AND (:searchCategoryCond IS NULL OR b.category = :searchCategoryCond)")
    Page<BoardEntity> searchByTitleOrContentAndCategory(@Param("searchContextCond") String searchContextCond,
                                                        @Param("searchCategoryCond") CafeName searchCategoryCond,
                                                        Pageable pageable);
}
