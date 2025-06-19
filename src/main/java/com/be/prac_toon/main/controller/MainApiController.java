package com.be.prac_toon.main.controller;

import com.be.prac_toon.main.dto.MainWebtoonListDto;
import com.be.prac_toon.content.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController             // 이 클래스가 REST API 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor    // 서비스 계층을 생성자 주입으로 받기 위해 추가
public class MainApiController {

    private final WebtoonService webtoonService; // 서비스 계층 의존성 주입

/*
    @GetMapping("/api/webtoons/mainbanner")
    public MainWebtoonListDto getMainbanner() {
        // 실제로는 별도의 로직으로 추천 웹툰을 선정해야 하지만,
        // 여기서는 간단히 '오늘의 업데이트' 첫 번째 웹툰을 반환하는 것으로 가정합니다.
        return webtoonService.getTodayWebtoons().stream().findFirst().orElse(null);
    }
    @GetMapping("/api/webtoons/today")
    public List<MainWebtoonListDto> getTodaysWebtoons() {
        // 테스트 데이터
//        List<MainWebtoonListDto> webtoons = Arrays.asList(new MainWebtoonListDto(1, "나 혼자만 레벨업", "추공", "https://via.placeholder.com/200x250.png?text=Webtoon+1"));

        List<MainWebtoonListDto> webtoons = webtoonService.getTodayWebtoons();
        return webtoons; // List<WebtoonDto> 객체는 자동으로 JSON 배열 형태로 변환되어 응답됩니다.
    }
    @GetMapping("/api/webtoons/popular")
    public List<MainWebtoonListDto> getPopularWebtoons() {
        List<MainWebtoonListDto> webtoons = webtoonService.getPopularWebtoons();
        return webtoons;
    }


*/
//   ===> RestFull API로 변경

    // 메인페이지 웹툰 목록을 조회하는 API
    // RestFull API
    @GetMapping("/api/webtoons")
    public List<MainWebtoonListDto> getWebtoons(
            // name=[파라미터명] : 넘겨받는 파라미터 이름 명시(매핑)
            // required=false : 이 파라미터가 필수가 아님을 의미
            @RequestParam(name = "featured", required = false) Boolean featured, // 메인배너
            @RequestParam(name = "category",required = false) String category
    ) {
        log.info("API 요청 수신: /api/webtoons - featured: {}, category: {}", featured, category);

        if(featured != null && featured) {

            // 서비스 계층에 추천 웹툰을 가져오는 로직을 호출 (결과가 하나일 수 있음)
//            return webtoonService.getFeaturedWebtoons(); // 이 메소드는 새로 만들어야 함

            // 1. 서비스에서 추천 웹툰을 Optional<DTO> 형태로 리턴
            return webtoonService.getFeaturedWebtoon()
                    .map(List::of)                      // 2. DTO가 존재하면(ifPresent), 그 DTO 하나만 담은 리스트를 생성
                    .orElse(Collections.emptyList());   // 3. DTO가 없으면(orElse), 빈 리스트를 반환

        } else if ("today".equalsIgnoreCase(category)) {
            return webtoonService.getTodayWebtoons();
        } else if ("popular".equalsIgnoreCase(category)) {
            return webtoonService.getPopularWebtoons();
        }

        return List.of(); // 해당하는 카테고리가 없으면 빈 목록 반환
    }
}
