package com.be.prac_toon.content.controller;

import com.be.prac_toon.content.dto.WebtoonCreateRequestDto;
import com.be.prac_toon.content.dto.WebtoonDetailDto;
import com.be.prac_toon.content.service.WebtoonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController             // 이 클래스가 REST API 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor    // 서비스 계층을 생성자 주입으로 받기 위해 추가
public class WebtoonApiController {

    private final WebtoonService webtoonService;

    // 웹툰 상세 정보 조회 API
    @GetMapping("/api/webtoons/{webtoonId}")
    public WebtoonDetailDto getWebtoonById(
            // @PathVariable: URL 경로에서 변수 값을 추출하여 메소드 파라미터로 전달
            @PathVariable(name = "webtoonId") Long webtoonId
    ) {
        return webtoonService.getWebtoonDetail(webtoonId);
    }

/*
    // 웹툰 생성 API (이미지 파일과 JSON 데이터를 함께 받음)
    @PostMapping("/api/webtoons")
    public ResponseEntity<Void> createWebtoon(

            // @Valid: WebtoonCreateRequestDto에 설정된 유효성 검사 규칙(@NotBlank 등)을 실행하라는 명령
            // @RequestPart: multipart/form-data 요청에서 파일과 JSON 데이터를 함께 받을 때 사용합니다.
            @Valid @RequestPart("requestDto") WebtoonCreateRequestDto requestDto,
            @RequestPart("thumbnailFile") MultipartFile thumbnailFile) throws IOException {

        webtoonService.createWebtoon(requestDto, thumbnailFile);

        // 성공적으로 생성되었음을 알리는 201 Created 응답 반환
        return ResponseEntity.status(201).build();
    }
*/
}
