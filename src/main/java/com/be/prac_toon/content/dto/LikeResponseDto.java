package com.be.prac_toon.content.dto;

import lombok.Getter;

@Getter
public class LikeResponseDto {

    private final boolean isLiked;
    private final int likeCount;

    public LikeResponseDto(boolean isLiked, int likeCount) {
        this.isLiked = isLiked;
        this.likeCount = likeCount;
    }

}
