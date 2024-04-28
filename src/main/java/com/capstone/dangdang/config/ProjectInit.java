//package com.capstone.dangdang.config;
//
//import com.capstone.dangdang.entity.CafeEntity;
//import com.capstone.dangdang.entity.DrinkEntity;
//import com.capstone.dangdang.enums.CafeName;
//import com.capstone.dangdang.repository.CafeRepository;
//import com.capstone.dangdang.repository.DrinkRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Stream;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class ProjectInit {
//
//    /**
//     * Cafe Image Path : ./Image/Cafe/{CafeName}  e.g) ./Image/cafe/스타벅스, ./Image/cafe/메가커피 ...
//     * Drink Image Path : ./Image/{CafeName}/{DrinkName}   e.g) ./Image/스타벅스/콜드_브루_커피  ./Image/메가커피/그린애플_펄_스무디 (띄어쓰기는 _로 저장)
//     */
//
//    private final CafeRepository cafeRepository;
//    private final DrinkRepository drinkRepository;
//    @Value("${file:}")
//    private String rootPath;
//    private static String CAFE_PREFIX = "Cafe";
//
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void initData() {
//        log.info("Init Data");
//
//        initCafe();
//    }
//
//    private void initCafe() {
//        for (CafeName value : CafeName.values()) {
//            Path path = Paths.get(rootPath, value.name()).toAbsolutePath();
//            List<CafeEntity> cafeEntityList = new ArrayList<>();
//            try {
//                Files.list(path)
//                        .filter(Files::isRegularFile)
//                        .forEach(c -> cafeEntityList.add(CafeEntity.builder()
//                                        .name(CafeName.valueOf(value.name())).build()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//            cafeRepository.saveAll(cafeEntityList);
//        }
//    }
//
//    private void initDrink() {
//        for (CafeName value : CafeName.values()) {
//            Path path = Paths.get(rootPath, value.name()).toAbsolutePath();
//            List<DrinkEntity> drinkEntityList = new ArrayList<>();
//
//            try (Stream<Path> paths = Files.walk(path)) {
//                paths.filter(Files::isRegularFile)
//                        .forEach(p -> {
//                            DrinkEntity.builder()
//                                    .name(p.getFileName().toString()::)
//                                    .
//                            DrinkEntity drink = new DrinkEntity();
//                            drinkEntityList.add(drink);
//                        });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//}
