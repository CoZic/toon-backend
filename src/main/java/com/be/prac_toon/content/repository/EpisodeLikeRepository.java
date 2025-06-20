package com.be.prac_toon.content.repository;

import com.be.prac_toon.content.domain.Episode;
import com.be.prac_toon.content.domain.EpisodeLike;
import com.be.prac_toon.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EpisodeLikeRepository extends JpaRepository<EpisodeLike, Long> {

    /// 특정 유저와 에피소드로 '좋아요' 존재 여부 확인
    Optional<EpisodeLike> findByUserAndEpisode(User user, Episode episode);

}
