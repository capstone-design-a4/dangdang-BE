package com.capstone.dangdang.service;

import com.capstone.dangdang.enums.CafeName;
import com.capstone.dangdang.error.CustomException;
import com.capstone.dangdang.error.ErrorCode;
import com.capstone.dangdang.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    @Value("${file:}")
    private String rootFilePath;
    private static String CAFE_PREFIX = "Cafe";

    public List<CafeName> getSupportedCafeList() {
        return Arrays.stream(CafeName.values()).toList();
    }

    public Resource getCafeImage(CafeName cafeName) {
        try {
            Path dirPath = Paths.get(rootFilePath, CAFE_PREFIX).toAbsolutePath();

            Optional<Path> imagePath = Files.walk(dirPath)
                    .filter(path -> path.getFileName().toString().matches(cafeName.name() + "\\..*"))
                    .findFirst();

            if (imagePath.isPresent()) {
                Resource imageResource = new UrlResource(imagePath.get().toUri());
                if (imageResource.exists()) {
                    return imageResource;
                } else {
                    throw new CustomException(ErrorCode.FILE_NOT_FOUND);
                }
            } else {
                throw new CustomException(ErrorCode.FILE_NOT_FOUND);
            }

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



}
