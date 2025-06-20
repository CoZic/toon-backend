package com.be.prac_toon.content.controller;

import com.be.prac_toon.content.dto.LikeResponseDto;
import com.be.prac_toon.content.service.EpisodeLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EpisodeLikeController {

    private final EpisodeLikeService episodeLikeService;

    @PostMapping("/episodes/{episodeId}/like")
    public ResponseEntity<LikeResponseDto> toggleLike(
            @PathVariable(name = "episodeId") Long episodeId
            // @AuthenticationPrincipal UserPrincipal userPrincipal // TODO: 로그인 기능 구현 후 주석 해제
    ) {

    // --- 이 부분을 로그인 기능 구현 전까지 테스트용으로 사용합니다. ---
        // TODO: 로그인 기능 구현 후, 아래 userId는 userPrincipal.getUserId() 로 대체해야 합니다.
        // 1. 로그인한 사용자를 테스트하고 싶을 때:
        Long currentUserId = 1L; // 1번 사용자가 로그인했다고 가정

        // 2. 비로그인 사용자를 테스트하고 싶을 때 (위 라인을 주석처리하고 아래 라인 주석 해제):
        // Long currentUserId = null;
    // -----------------------------------------------------------------

        if (currentUserId == null) {
            // 권한 없음 응답
            return ResponseEntity.status(401).build();
        }

        LikeResponseDto response = episodeLikeService.toggleLike(episodeId, currentUserId);

        // 좋아요 상태와 좋아요 수를 포함한 응답 반환
        return ResponseEntity.ok(response);
    }

}
