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


-- INSERT IGNORE : Primary Key가 중복될 경우, 에러를 발생시키지 않고 조용히 해당 INSERT를 무시합니다.
-- ★★★ MySQL/MariaDB에서만 사용 가능, 빌드 시 h2 데이터베이스를 사용하기 때문에 빌드 에러가 발생하여 사용안하게 수정

-- 'today' 카테고리 데이터
INSERT INTO webtoon (title, author, thumbnail_url, category) VALUES
('나 혼자만 레벨업', '추공', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+1', 'today'),
('전지적 독자 시점', '싱숑', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+2', 'today'),
('화산귀환', '비가', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+3', 'today'),
('세이렌', '설레다', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+4', 'today'),
('입학용병', 'YC', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+5', 'today')
;

-- 'popular' 카테고리 데이터
INSERT INTO webtoon (title, author, thumbnail_url, category) VALUES
('알고있지만', '정서', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+8', 'popular'),
('유미의 세포들', '이동건', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+7', 'popular'),
('신의 탑', 'SIU', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+10', 'popular'),
('외모지상주의', '박태준', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+9', 'popular'),
('더 복서', '정지훈', 'https://via.placeholder.com/200x250.png?text=DB+Webtoon+11', 'popular')
;