package com.be.prac_toon.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 합친 것
@NoArgsConstructor  // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
public class WebtoonListDto {

    private long id;
    private String title;
    private String author;
    private String thumbnailUrl;

}
