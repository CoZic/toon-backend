package com.be.prac_toon.webtoon.service;

import com.be.prac_toon.common.utill.imageUpload.ImageUploadService;
import com.be.prac_toon.webtoon.domain.Webtoon;
import com.be.prac_toon.main.dto.MainWebtoonListDto;
import com.be.prac_toon.webtoon.dto.WebtoonCreateRequestDto;
import com.be.prac_toon.webtoon.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service // 이 클래스가 비즈니스 로직을 처리하는 서비스 계층임을 나타냅니다.
@Transactional(readOnly = true) // 클래스 레벨: 모든 public 메소드에 기본으로 '읽기 전용' 적용
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 만들어줍니다. (생성자 주입)
public class WebtoonService {

    private final ImageUploadService imageUploadService;    // 이미지 업로드 서비스 의존성 주입
    private final WebtoonRepository webtoonRepository;      // 웹툰 데이터베이스 레포지토리 의존성 주입

    // 메인 > '오늘의 업데이트' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getTodayWebtoons() {

        // 'today' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
        List<Webtoon> webtoons = webtoonRepository.findByCategory("today");

        // 조회된 Entity 목록을 DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(webtoon ->
                        new MainWebtoonListDto(
                            webtoon.getId(), webtoon.getTitle(), webtoon.getAuthor(), webtoon.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

    // 메인 > '인기 TOP 10' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getPopularWebtoons() {

        // 'popular' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
        List<Webtoon> webtoons = webtoonRepository.findByCategory("popular");

        // DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(webtoon ->
                        new MainWebtoonListDto(
                            webtoon.getId(), webtoon.getTitle(), webtoon.getAuthor(), webtoon.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

    // 웹툰 썸네일 이미지 업로드
    @Transactional  // '쓰기' 메소드: 클래스 설정을 덮어쓰고 readOnly=false인 기본 트랜잭션 적용
    public void createWebtoon(WebtoonCreateRequestDto requestDto, MultipartFile thumbnailFile) throws IOException {
        // 1. 이미지 파일 처리
        String thumbnailUrl = imageUploadService.storeFile(thumbnailFile);

        // 2. DTO와 이미지 URL을 바탕으로 Entity 생성
        Webtoon webtoon = Webtoon.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .thumbnailUrl(thumbnailUrl) // 실제 저장된 이미지 경로를 사용
                .category(requestDto.getCategory())
                .build();

        // 3. 데이터베이스에 저장
        webtoonRepository.save(webtoon);
    }



}
