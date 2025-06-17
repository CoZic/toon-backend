package com.be.prac_toon.main.controller;

import com.be.prac_toon.main.dto.MainWebtoonDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MainApiController {

    @GetMapping("/api/webtoons/today")
    public List<MainWebtoonDto> getTodaysWebtoons() {
        // 실제로는 DB에서 조회해야 할 데이터를 임시로 생성합니다.
        List<MainWebtoonDto> webtoons = Arrays.asList(
                new MainWebtoonDto(1, "나 혼자만 레벨업", "추공", "https://via.placeholder.com/200x250.png?text=Webtoon+1"),
                new MainWebtoonDto(2, "전지적 독자 시점", "싱숑", "https://via.placeholder.com/200x250.png?text=Webtoon+2"),
                new MainWebtoonDto(3, "화산귀환", "비가", "https://via.placeholder.com/200x250.png?text=Webtoon+3"),
                new MainWebtoonDto(4, "세이렌", "설레다", "https://via.placeholder.com/200x250.png?text=Webtoon+4"),
                new MainWebtoonDto(5, "입학용병", "YC", "https://via.placeholder.com/200x250.png?text=Webtoon+5")
        );
        return webtoons; // List<WebtoonDto> 객체는 자동으로 JSON 배열 형태로 변환되어 응답됩니다.
    }

/*
*/
    @GetMapping("/api/webtoons/popular")
    public List<MainWebtoonDto> getPopularWebtoons() {
        // 실제로는 DB에서 조회해야 할 데이터를 임시로 생성합니다.
        List<MainWebtoonDto> webtoons = Arrays.asList(
                new MainWebtoonDto(6, "알고있지만", "정서", "https://via.placeholder.com/200x250.png?text=Webtoon+6"),
                new MainWebtoonDto(7, "유미의 세포들", "이동건", "https://via.placeholder.com/200x250.png?text=Webtoon+7"),
                new MainWebtoonDto(8, "신의 탑", "SIU", "https://via.placeholder.com/200x250.png?text=Webtoon+8"),
                new MainWebtoonDto(9, "외모지상주의", "박태준", "https://via.placeholder.com/200x250.png?text=Webtoon+9"),
                new MainWebtoonDto(10, "더 복서", "정지훈", "https://via.placeholder.com/200x250.png?text=Webtoon+10")
        );
        return webtoons;
    }
}
