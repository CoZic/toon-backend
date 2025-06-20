package com.be.prac_toon.content.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class EpisodeViewerDto {

    private Long episodeId;
    private String webtoonTitle;
    private String episodeTitle;
    private List<String> imageUrls;
    private Long prevEpisodeId; // 이전 화 ID (없으면 null)
    private Long nextEpisodeId; // 다음 화 ID (없으면 null)

    private int likeCount;      // 해당 에피소드의 총 좋아요 수
    private boolean liked;      // 현재 사용자가 좋아요를 눌렀는지 여부

    public EpisodeViewerDto(Long episodeId, String webtoonTitle, String episodeTitle, List<String> imageUrls, Long prevEpisodeId, Long nextEpisodeId, int likeCount, boolean liked) {
        this.episodeId = episodeId;
        this.webtoonTitle = webtoonTitle;
        this.episodeTitle = episodeTitle;
        this.imageUrls = imageUrls;
        this.prevEpisodeId = prevEpisodeId;
        this.nextEpisodeId = nextEpisodeId;
        this.likeCount = likeCount;
        this.liked = liked;
    }

}
