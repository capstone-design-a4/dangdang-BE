package com.capstone.dangdang.controller;

import com.capstone.dangdang.dto.entity.CafeDto;
import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.service.CafeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Cafe")
@RequestMapping("/api/cafe")
public class CafeController {

    private final CafeService cafeService;

    @GetMapping("/support")
    @Operation(summary = "지원하는 카페 목록 조회")
    public List<CafeName> getCafeList() {
        return cafeService.getSupportedCafeList();
    }

    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "카페 이미지 조회")
    public Resource getCafeImage(@PathVariable CafeName name) {
        return cafeService.getCafeImage(name);
    }



}
