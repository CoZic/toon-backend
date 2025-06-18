package com.be.prac_toon.main.controller;

import com.be.prac_toon.main.dto.MainWebtoonListDto;
import com.be.prac_toon.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController             // 이 클래스가 REST API 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor    // 서비스 계층을 생성자 주입으로 받기 위해 추가
public class MainApiController {

    private final WebtoonService webtoonService; // 서비스 계층 의존성 주입

    @GetMapping("/api/webtoons/today")
    public List<MainWebtoonListDto> getTodaysWebtoons() {

        // 실제로는 DB에서 조회해야 할 데이터를 임시로 생성합니다.
        /*
        List<MainWebtoonListDto> webtoons = Arrays.asList(
                new MainWebtoonListDto(1, "나 혼자만 레벨업", "추공", "https://via.placeholder.com/200x250.png?text=Webtoon+1"),
                new MainWebtoonListDto(2, "전지적 독자 시점", "싱숑", "https://via.placeholder.com/200x250.png?text=Webtoon+2"),
                new MainWebtoonListDto(3, "화산귀환", "비가", "https://via.placeholder.com/200x250.png?text=Webtoon+3"),
                new MainWebtoonListDto(4, "세이렌", "설레다", "https://via.placeholder.com/200x250.png?text=Webtoon+4"),
                new MainWebtoonListDto(5, "입학용병", "YC", "https://via.placeholder.com/200x250.png?text=Webtoon+5")
        );
        */

        List<MainWebtoonListDto> webtoons = webtoonService.getTodayWebtoons();

        return webtoons; // List<WebtoonDto> 객체는 자동으로 JSON 배열 형태로 변환되어 응답됩니다.
    }

/*
*/
    @GetMapping("/api/webtoons/popular")
    public List<MainWebtoonListDto> getPopularWebtoons() {

        // 실제로는 DB에서 조회해야 할 데이터를 임시로 생성합니다.
        /*
        List<MainWebtoonListDto> webtoons = Arrays.asList(
                new MainWebtoonListDto(6, "알고있지만", "정서", "https://via.placeholder.com/200x250.png?text=Webtoon+6"),
                new MainWebtoonListDto(7, "유미의 세포들", "이동건", "https://via.placeholder.com/200x250.png?text=Webtoon+7"),
                new MainWebtoonListDto(8, "신의 탑", "SIU", "https://via.placeholder.com/200x250.png?text=Webtoon+8"),
                new MainWebtoonListDto(9, "외모지상주의", "박태준", "https://via.placeholder.com/200x250.png?text=Webtoon+9"),
                new MainWebtoonListDto(10, "더 복서", "정지훈", "https://via.placeholder.com/200x250.png?text=Webtoon+10")
        );
        */

        List<MainWebtoonListDto> webtoons = webtoonService.getPopularWebtoons();

        return webtoons;
    }
}
