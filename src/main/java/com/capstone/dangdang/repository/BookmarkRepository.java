package com.capstone.dangdang.repository;

import com.capstone.dangdang.entity.BookmarkEntity;
import com.capstone.dangdang.entity.DrinkEntity;
import com.capstone.dangdang.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    boolean existsByMember_IdAndDrink_Id(Long memberId, Long drinkId);
    Optional<BookmarkEntity> findByMember_IdAndDrink_Id(Long memberId, Long drinkId);

    List<BookmarkEntity> findByMember_Id(Long memberId);

    @Query("SELECT b.drink.id FROM BookmarkEntity b WHERE b.member = :member AND b.drink.id IN :drinkIds")
    List<Long> findBookmarkedDrinkByMemberAndDrink(@Param("member") MemberEntity member, @Param("drinkIds") List<Long> drinkIds);
}
