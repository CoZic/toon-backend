package com.be.prac_toon.content.dto;

import com.be.prac_toon.content.domain.Content;
import lombok.Getter;

import java.util.List;

@Getter
public class WebtoonDetailDto {

    private Long id;
    private String title;
    private String author;
    private String thumbnailUrl;
    private String description;
    private List<EpisodeDto> episodes; // 해당 웹툰의 에피소드 목록
    private int totalEpisodes; // 총 회차 수 필드 추가

    // Entity를 DTO로 변환하는 생성자
    public WebtoonDetailDto(Content content, List<EpisodeDto> episodes) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.author = content.getAuthor();
        this.thumbnailUrl = content.getThumbnailUrl();
        this.description = content.getDescription();
        this.episodes = episodes;
        this.totalEpisodes = content.getEpisodes().size(); // episodes 리스트의 크기로 총 회차 수 설정
    }

}
