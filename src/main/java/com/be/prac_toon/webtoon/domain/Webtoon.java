package com.be.prac_toon.webtoon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity                     // 이 클래스가 데이터베이스 테이블과 매핑되는 JPA Entity임을 나타냅니다.
@Table(name = "webtoon")    // 이 Entity 클래스를 'webtoon'이라는 이름의 테이블에 매핑하라는 명시적인 설정
@Getter                     // 모든 필드에 대한 Getter 메소드를 자동으로 생성합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터 없는 기본 생성자를 보호된 접근 수준으로 생성합니다.
public class Webtoon {

    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다. (Auto-increment)
    private Long id;

    private String title; // 웹툰 제목

    private String author; // 작가

    private String thumbnailUrl; // 썸네일 이미지 URL

    private String category; // 'today', 'popular' 등 카테고리


    @Builder // 빌더 패턴을 사용하여 객체를 생성할 수 있게 합니다.
    public Webtoon(String title, String author, String thumbnailUrl, String category) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.category = category;
    }

}
