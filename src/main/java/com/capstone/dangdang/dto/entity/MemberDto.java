package com.capstone.dangdang.dto.entity;

import com.capstone.dangdang.entity.MemberEntity;
import com.capstone.dangdang.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String name;
    private Gender gender;
    private String imageUrl;
    private RecordDto record;
    private LocalDate birth;

    public MemberDto(MemberEntity member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.imageUrl = member.getImageUrl();
        this.record = new RecordDto(member.getRecord());
        this.birth = member.getBirth();
    }
}
