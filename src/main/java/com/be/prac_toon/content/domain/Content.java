package com.be.prac_toon.content.domain;

import com.be.prac_toon.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Content 클래스는 웹툰 콘텐츠를 나타내는 JPA Entity입니다.
 */
@Entity                     // 이 클래스가 데이터베이스 테이블과 매핑되는 JPA Entity임을 나타냅니다.
@Table(name = "content")    // 이 Entity 클래스를 'Content'이라는 이름의 테이블에 매핑하라는 명시적인 설정
@Getter                     // 모든 필드에 대한 Getter 메소드를 자동으로 생성합니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터 없는 기본 생성자를 보호된 접근 수준으로 생성합니다.
public class Content {

    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다. (Auto-increment)
    @Column(name = "content_id") // DB와 컬럼과 매핑
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrant_id") // DB의 외래 키 컬럼 이름
    private User registrant; // 등록한 User 객체를 참조

    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    @Column(nullable = false)
    private ContentType contentType; // 'WEBTOON', 'VIDEO' 등을 저장할 타입

    @Column(nullable = false)
    private String title; // 웹툰 제목

    @Column(nullable = false)
    private String author; // 작가

    private int ageRating; // 연령 등급 (예: 전체 이용가, 12세 이상, 15세 이상 등)

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl; // 썸네일 이미지 URL

    private int viewCount; // 조회수

    private int likeCount; // 좋아요 수

    /*
        연재 요일을 나타내는 필드
        @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.LAZY)
            JPA에게
            이 serializationDays 필드는 content 테이블의 단순한 컬럼이 아니야.
            이것은 DayOfWeek라는 값들의 컬렉션이니, 이것들을 저장할 별도의 공간(테이블)을 준비해줘.
            그리고 성능을 위해, 내가 content.getSerializationDays()를 호출하기 전까지는 절대 미리 불러오지 마(LAZY loading).

        @CollectionTable(name = "content_serialization_day", joinColumns = @JoinColumn(name = "content_id"))
            - 위에서 말한 '별도의 공간'이 어떤 모습일지에 대한 상세 설계도
            DayOfWeek 값들을 저장할 새 테이블의 이름은 content_serialization_day로 해줘.
            그리고 이 테이블에는 content_id라는 이름의 컬럼을 만들어서, 어떤 Content에 속한 요일들인지 알 수 있도록 연결(Join)해줘.
    */
    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.LAZY)                               // '이 필드가 값 타입(Enum, String 등)의 컬렉션임을 나타냅니다.
    @CollectionTable(name = "content_serialization_day", joinColumns = @JoinColumn(name = "content_id"))    // 'content_serialization_day'라는 테이블에 매핑하고, 'content_id' 컬럼을 외래 키로 사용합니다.
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week") // DB의 컬럼 이름을 'day_of_week'로 지정
    private Set<DayOfWeek> serializationDays = new HashSet<>(); // 연재 요일

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    // '일'에 해당하는 content이 '다'에 해당하는 Episode 목록을 가집니다.
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Episode> episodes = new ArrayList<>();


    @Builder // 빌더 패턴을 사용하여 객체를 생성할 수 있게 합니다.
    public Content(User registrant, ContentType contentType, String title, String author, int ageRating, String description, String thumbnailUrl, Set<DayOfWeek> serializationDays) {
        this.registrant = registrant;
        this.contentType = contentType;
        this.title = title;
        this.author = author;
        this.ageRating = ageRating;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.serializationDays = serializationDays;
        this.viewCount = 0;
        this.likeCount = 0;
    }

}
