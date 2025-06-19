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

    public EpisodeViewerDto(Long episodeId, String webtoonTitle, String episodeTitle, List<String> imageUrls, Long prevEpisodeId, Long nextEpisodeId) {
        this.episodeId = episodeId;
        this.webtoonTitle = webtoonTitle;
        this.episodeTitle = episodeTitle;
        this.imageUrls = imageUrls;
        this.prevEpisodeId = prevEpisodeId;
        this.nextEpisodeId = nextEpisodeId;
    }

}
