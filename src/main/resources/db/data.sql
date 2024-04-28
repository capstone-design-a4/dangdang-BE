INSERT INTO cafe (id, name) VALUES (1, '스타벅스') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (2, '메가커피') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (3, '컴포즈커피') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (4, '빽다방') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (5, '이디야커피') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (6, '투썸플레이스') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (7, '엔젤리너스') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (8, '공차') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (9, '청자다방') ON DUPLICATE KEY UPDATE id=id;
INSERT INTO cafe (id, name) VALUES (10, '벌크커피') ON DUPLICATE KEY UPDATE id=id;

INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (1, '나이트로 바닐라 크림', 0, 10, '나이트로_바닐라_크림', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (2, '피스타치오 크림 콜드 브루', 10, 150, '피스타치오_크림_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (3, '나이트로 콜드 브루', 5, 50, '나이트로_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (4, '리저브 나이트로', 0, 5, '리저브_나이트로', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (5, '리저브 콜드 브루', 0, 5, '리저브_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (6, '민트 콜드 브루', 24, 120, '민트_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (7, '바닐라 크림 콜드 브루', 24, 190, '바닐라_크림_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (8, '시그니처 더 블랙 콜드 브루', 0, 5, '시그니처_더_블랙_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (9, '씨솔트 카라멜 콜드 브루', 24, 190, '씨솔트_카라멜_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (10, '여수 윤슬 헤이즐넛 콜드브루', 24, 120, '여수_윤슬_헤이즐넛_콜드브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (11, '오트 콜드 브루', 24, 190, '오트_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (12, '제주 비자림 콜드 브루', 0, 5, '제주_비자림_콜드_브루', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (13, '콜드 브루 몰트', 24, 190, '콜드_브루_몰트', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (14, '콜드 브루 플로트', 24, 190, '콜드_브루_플로트', 1);
INSERT IGNORE INTO drink (id, name, sugar, calorie, image_url, cafe_id) VALUES (15, '콜드 브루', 0, 5, '콜드_브루', 1);

