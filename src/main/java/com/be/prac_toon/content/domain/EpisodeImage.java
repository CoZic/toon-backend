package com.be.prac_toon.content.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EpisodeImage 클래스는 웹툰 에피소드의 이미지를 나타내는 JPA Entity입니다.
 * 이 클래스는 Episode와 연관되어 있으며, 에피소드에 대한 이미지 정보를 저장합니다.
 */
@Entity                                             // 이 클래스가 데이터베이스 테이블과 매핑되는 JPA Entity임을 나타냅니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 파라미터 없는 기본 생성자를 보호된 접근 수준으로 생성합니다.
public class EpisodeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeImageId;

    // Episode와의 다대일 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    private String imageUrl;

    private int imageOrder; // 이미지 순서

}
