package com.be.prac_toon.content.domain;

import com.be.prac_toon.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 한 명의 유저가 한 에피소드에 중복으로 좋아요를 누를 수 없도록 Unique 제약조건 설정
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "episode_id"})
})
public class EpisodeLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    public EpisodeLike(User user, Episode episode) {
        this.user = user;
        this.episode = episode;
    }

}
