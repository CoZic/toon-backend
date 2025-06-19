package com.be.prac_toon.content.dto;

import com.be.prac_toon.content.domain.Episode;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EpisodeDto {

    private Long id;
    private int episodeNumber;
    private String title;
    private String thumbnailUrl;
    private Instant createdAt;

    public EpisodeDto(Episode episode) {
        this.id = episode.getEpisodeId();
        this.episodeNumber = episode.getEpisodeNumber();
        this.title = episode.getTitle();
        this.thumbnailUrl = episode.getThumbnailUrl();
        this.createdAt = episode.getCreatedAt();
    }

}
