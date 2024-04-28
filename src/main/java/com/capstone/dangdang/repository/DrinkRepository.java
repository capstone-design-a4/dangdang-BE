package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.CafeEntity;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.CafeName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

    List<DrinkEntity> findByCafe_Name(CafeName cafeName);

    @Query("SELECT b.drink FROM BookmarkEntity b WHERE b.member = :member")
    List<DrinkEntity> findDrinksByMember(MemberEntity member);

    @Query("SELECT d FROM DrinkEntity d LEFT JOIN BookmarkEntity b ON d.id = b.drink.id GROUP BY d.id ORDER BY COUNT(b.id) DESC limit 4")
    List<DrinkEntity> findTop10ByBookmarkCount();
    Page<DrinkEntity> findByNameContainingAndCafe_Name(String name, CafeName cafeName, Pageable pageable);


}
