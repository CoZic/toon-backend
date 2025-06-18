package com.be.prac_toon.webtoon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebtoonCreateRequestDto {

    @NotBlank(message = "웹툰 제목은 필수 입력 항목입니다.")
    @Size(max = 100, message = "웹툰 제목은 100자를 초과할 수 없습니다.")
    private String title;

    @NotBlank(message = "작가 이름은 필수 입력 항목입니다.")
    @Size(max = 50, message = "작가 이름은 50자를 초과할 수 없습니다.")
    private String author;

    @NotBlank(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

}
