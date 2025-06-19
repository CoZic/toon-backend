package com.be.prac_toon.content.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    /*
        에피소드에 속한 이미지들을 저장하는 리스트
        @OneToMany(...): 하나의 에피소드는 여러 개의 에피소드 이미지를 가진다는 '일대다' 관계를 설정
        mappedBy = "episode": 이 관계의 주인은 EpisodeImage Entity에 있는 episode 필드임을 명시
                              이 설정을 통해 JPA는 이 관계를 관리하는 외래 키가 episode_image 테이블에 있다는 것을 알게 됩니다.
        cascade, fetch 등: Webtoon와 Episode의 관계와 동일하게, 부모(Episode)의 생명주기에 자식(EpisodeImage)이 따르도록 하고,
                          성능을 위해 지연 로딩을 사용합니다.
    */
    @OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EpisodeImage> episodeImages = new ArrayList<>();

}
