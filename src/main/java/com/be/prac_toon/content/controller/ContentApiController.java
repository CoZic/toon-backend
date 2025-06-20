package com.be.prac_toon.content.controller;

import com.be.prac_toon.content.domain.ContentType;
import com.be.prac_toon.content.dto.EpisodeViewerDto;
import com.be.prac_toon.content.dto.WebtoonDetailDto;
import com.be.prac_toon.content.dto.WebtoonListDto;
import com.be.prac_toon.content.service.ContentService;
import com.be.prac_toon.content.service.ContentServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController             // 이 클래스가 REST API 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor    // 서비스 계층을 생성자 주입으로 받기 위해 추가
public class ContentApiController {

    private final ContentServiceFactory contentServiceFactory;
    private final ContentService webtoonService; // 메인 페이지는 웹툰만 보여주므로 직접 주입

// =============================================================================================

    // 메인 페이지용 API (이전 MainApiController의 내용)
    @GetMapping("/api/webtoons")
    public List<WebtoonListDto> getMainWebtoons(@RequestParam(name = "category") String category) {
        log.info("API 요청 수신: /api/webtoons - category: {}", category);

        // 메인배너 요청
        if ("featured".equals(category)) {
            return webtoonService.getFeaturedContent().map(List::of).orElse(List.of());
        }
        // 메인 > 인기 TOP5 웹툰 리스트 요청
        else if ("popular".equals(category)) {
            return webtoonService.getPopularContent();
        }
        // 메인 > 오늘의 업데이트 웹툰 리스트 요청
        else if ("today".equals(category)) {
            return webtoonService.getTodayContent();
        }

        // 지원하지 않는 타입인 경우 빈 리스트 반환
        return List.of();
    }

    // 웹툰 상세 페이지용 API
    @GetMapping("/api/webtoons/{webtoonId}")
    public WebtoonDetailDto getContentById(@PathVariable(name = "webtoonId") Long webtoonId) {

        ContentType type = contentServiceFactory.getContentTypeById(webtoonId);

        // 콘텐츠 타입에 맞는 서비스 인스턴스를 가져옴
        ContentService service = contentServiceFactory.getService(type);

        // 해당 콘텐츠의 상세 정보를 조회
        return service.getContentDetail(webtoonId);
    }

    // 뷰어 페이지용 API
    @GetMapping("/api/episodes/{episodeId}")
    public EpisodeViewerDto getEpisodeViewerById(@PathVariable(name = "episodeId") Long episodeId) {
        // 뷰어는 특정 콘텐츠 타입에 종속되지 않으므로, 하나의 서비스에서 처리 가능
        // (실제로는 episodeId로 타입을 알아내서 맞는 서비스를 호출해야 함)
        ContentService service = contentServiceFactory.getService(ContentType.WEBTOON); // 임시로 웹툰 서비스 사용
        return service.getEpisodeViewer(episodeId);
    }


// =============================================================================================
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
