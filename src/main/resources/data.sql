-- 데이터가 이미 존재할 경우 중복 삽입을 방지하려면 아래 라인을 활성화하세요.
--CREATE TABLE webtoon (
--    id bigint not null auto_increment,
--    author varchar(255),
--    category varchar(255),
--    thumbnail_url varchar(255),
--    title varchar(255),
--    primary key (id)
--)
-- DROP TABLE webtoon;
-- DELETE FROM webtoon;
-- DELETE from webtoon where id in ('16', '17', '18', '19', '20');


-- INSERT IGNORE : Primary Key가 중복될 경우, 에러를 발생시키지 않고 조용히 해당 INSERT를 무시합니다.
-- ★★★ MySQL/MariaDB에서만 사용 가능, 빌드 시 h2 데이터베이스를 사용하기 때문에 빌드 에러가 발생하여 사용안하게 수정

-- popular(인기 TOP 10) 카테고리 데이터
INSERT INTO webtoon (title, author, thumbnail_url, category) VALUES
('나 혼자만 레벨업', '추공', '/uploads/images/only-i-level-up.jfif', 'popular'),
('전지적 독자 시점', '싱숑', '/uploads/images/omniscient-reader.jpg', 'popular'),
('화산귀환', '비가', '/uploads/images/return-of-the-blossoming-blade.jpg', 'popular'),
('신의 탑', 'SIU', '/uploads/images/towerofgod.jpg', 'popular'),
('외모지상주의', '박태준', '/uploads/images/looksim.jpg', 'popular')
;

-- today(오늘의 업데이트) 카테고리 데이터
INSERT INTO webtoon (title, author, thumbnail_url, category) VALUES
('세이렌', '설레다', '/uploads/images/seiren.jfif', 'today'),
('더 복서', '정지훈', '/uploads/images/the-boxer.jpg', 'today'),
('알고있지만', '정서', '/uploads/images/nevertheless.jpg', 'today'),
('입학용병', 'YC', '/uploads/images/teenage-mercenary.jpg', 'today'),
('유미의 세포들', '이동건', '/uploads/images/yumis-cells.jpg', 'today')
;
