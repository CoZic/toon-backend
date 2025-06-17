package com.be.prac_toon.main.service;

import com.be.prac_toon.main.domain.MainWebtoonList;
import com.be.prac_toon.main.dto.MainWebtoonListDto;
import com.be.prac_toon.main.repository.MainWebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // 이 클래스가 비즈니스 로직을 처리하는 서비스 계층임을 나타냅니다.
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 만들어줍니다. (생성자 주입)
@Transactional(readOnly = true) // 클래스 전체에 읽기 전용 트랜잭션을 적용합니다.
public class MainWebtoonService {

    private final MainWebtoonRepository mainWebtoonRepository;

    // '오늘의 업데이트' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getTodayWebtoons() {

        // 'today' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
        List<MainWebtoonList> webtoons = mainWebtoonRepository.findByCategory("today");

        // 조회된 Entity 목록을 DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(webtoon -> new MainWebtoonListDto(webtoon.getId(), webtoon.getTitle(), webtoon.getAuthor(), webtoon.getThumbnailUrl()))
                .collect(Collectors.toList());
    }

    // '인기 TOP 10' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getPopularWebtoons() {

        // 'popular' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
        List<MainWebtoonList> webtoons = mainWebtoonRepository.findByCategory("popular");

        // DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(webtoon -> new MainWebtoonListDto(webtoon.getId(), webtoon.getTitle(), webtoon.getAuthor(), webtoon.getThumbnailUrl()))
                .collect(Collectors.toList());
    }

}
