package com.be.prac_toon.content.service;

import com.be.prac_toon.content.domain.Episode;
import com.be.prac_toon.content.domain.EpisodeLike;
import com.be.prac_toon.content.dto.LikeResponseDto;
import com.be.prac_toon.content.repository.EpisodeLikeRepository;
import com.be.prac_toon.content.repository.EpisodeRepository;
import com.be.prac_toon.user.domain.User;
import com.be.prac_toon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeLikeService {

    private final EpisodeLikeRepository episodeLikeRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    @Transactional // 데이터를 변경하므로 readOnly=false
    public LikeResponseDto toggleLike(Long episodeId, Long userId) {

        // 사용자 ID와 에피소드 ID로 각 Entity를 찾음 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("에피소드를 찾을 수 없습니다."));

        // 이미 '좋아요'를 눌렀는지 확인
        Optional<EpisodeLike> existingLike = episodeLikeRepository.findByUserAndEpisode(user, episode);

        if (existingLike.isPresent()) {
            // 이미 좋아요를 눌렀다면 -> '좋아요 취소' 로직
            episodeLikeRepository.delete(existingLike.get());
            episode.decreaseLikeCount();
            return new LikeResponseDto(false, episode.getLikeCount());
        } else {
            // 좋아요를 누르지 않았다면 -> '좋아요' 로직
            EpisodeLike newLike = new EpisodeLike(user, episode);
            episodeLikeRepository.save(newLike);
            episode.increaseLikeCount();
            return new LikeResponseDto(true, episode.getLikeCount());
        }
    }

}
