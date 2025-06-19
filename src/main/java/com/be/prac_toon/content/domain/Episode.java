package com.be.prac_toon.content.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity                                             // 이 클래스가 데이터베이스 테이블과 매핑되는 JPA Entity임을 나타냅니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 파라미터 없는 기본 생성자를 보호된 접근 수준으로 생성합니다.
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // 기본 키 생성을 데이터베이스에 위임합니다. (Auto-increment)
    private Long episodeId;

    // '다'에 해당하는 Episode가 '일'에 해당하는 Webtoon을 참조합니다.
    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩 설정
    @JoinColumn(name = "content_id")    // DB의 외래 키 컬럼 이름 지정
    private Content content;

    private int episodeNumber;

    private String title;

    private String thumbnailUrl;

    private boolean free;

    @CreationTimestamp
    private Instant createdAt;


}
