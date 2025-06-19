package com.be.prac_toon.content.service;

import com.be.prac_toon.common.utill.imageUpload.ImageUploadService;
import com.be.prac_toon.content.domain.Content;
import com.be.prac_toon.content.domain.DayOfWeek;
import com.be.prac_toon.main.dto.MainWebtoonListDto;
import com.be.prac_toon.content.dto.EpisodeDto;
import com.be.prac_toon.content.dto.WebtoonCreateRequestDto;
import com.be.prac_toon.content.dto.WebtoonDetailDto;
import com.be.prac_toon.content.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service                        // 이 클래스가 비즈니스 로직을 처리하는 서비스 계층임을 나타냅니다.
@Transactional(readOnly = true) // 클래스 레벨: 모든 public 메소드에 기본으로 '읽기 전용' 적용
@RequiredArgsConstructor        // final로 선언된 필드에 대한 생성자를 자동으로 만들어줍니다. (생성자 주입)
public class WebtoonService {

    private final ImageUploadService imageUploadService;    // 이미지 업로드 서비스 의존성 주입
    private final WebtoonRepository webtoonRepository;      // 웹툰 데이터베이스 레포지토리 의존성 주입

// 추천 웹툰(메인 배너용)을 조회하는 메소드
    public Optional<MainWebtoonListDto> getFeaturedWebtoon() {

    // 조회 목록 중 랜덤으로 하나를 선택하는 로직(임시) =========
        // 1. 'today' 카테고리에 해당하는 웹툰 목록을 DB에서 조회
        List<Content> webtoons = webtoonRepository.findTop5ByOrderByViewCountDesc();

        // 2. 추천 웹툰이 없을 경우 Optional.empty() 반환
        if (webtoons.isEmpty()) {
            return Optional.empty();
        }

        // 3. 0부터 (리스트 크기 - 1) 사이의 랜덤한 인덱스를 생성
        int randomIndex = new Random().nextInt(webtoons.size());

        // 4. 랜덤 인덱스를 사용해 리스트에서 웹툰 하나를 선택
        Content randomContent = webtoons.get(randomIndex);

        // 5. 선택된 웹툰을 DTO로 변환하여 Optional로 감싸서 반환
        return Optional.of(new MainWebtoonListDto(
                randomContent.getId(),
                randomContent.getTitle(),
                randomContent.getAuthor(),
                randomContent.getThumbnailUrl())
        );
    }

// 메인 > '오늘의 업데이트' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getTodayWebtoons() {

        // 'today' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
//        List<Content> contents = webtoonRepository.findByCategory("today");

    // =================================================================================================================

        // 1. Java 표준 라이브러리를 사용해 Enum타입으로 오늘 날짜의 요일을 가져옵니다 (예: MONDAY, TUESDAY...).
        java.time.DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        log.info("오늘의 요일: {}", currentDay);

        // 2. Java의 DayOfWeek를 우리가 만든 Enum으로 변환합니다.
        DayOfWeek todayEnum = DayOfWeek.valueOf(currentDay.name());
        log.info("오늘의 요일 Enum: {}", todayEnum);

        // 3. 해당 요일의 웹툰들을 DB에서 조회합니다.
        List<Content> webtoons = webtoonRepository.findBySerializationDay(todayEnum);

    // =================================================================================================================
        // 추후 실제 웹툰 연재 등록 시 업데이트 목록에 뜰 수 있도록 하는 로직
        // 1. 현재 시간으로부터 24시간 전 시점을 계산합니다.
//        Instant twentyFourHoursAgo = Instant.now().minus(24, ChronoUnit.HOURS);

        // 2. 24시간 이내에 에피소드가 등록된 콘텐츠 목록을 DB에서 조회합니다.
//        List<Content> webtoons = webtoonRepository.findRecentlyUpdatedContent(twentyFourHoursAgo);
    // =================================================================================================================

        // 조회된 Entity 목록을 DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(content ->
                        new MainWebtoonListDto(
                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

// 메인 > '인기 TOP 10' 웹툰 목록을 조회하는 메소드
    public List<MainWebtoonListDto> getPopularWebtoons() {

        // 'popular' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
//        List<Content> contents = webtoonRepository.findByCategory("popular");

    // =================================================================================================================
        List<Content> webtoons = webtoonRepository.findTop5ByOrderByViewCountDesc();

        // DTO 목록으로 변환하여 반환합니다.
        return webtoons.stream()
                .map(content ->
                        new MainWebtoonListDto(
                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

// =====================================================================================================================

    // 웹툰 상세 정보 조회 메소드
    public WebtoonDetailDto getWebtoonDetail(Long webtoonId) {

        // ID로 웹툰을 찾고, 없으면 예외를 발생시킵니다.
        Content webtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 웹툰을 찾을 수 없습니다. id=" + webtoonId));

        log.info("웹툰 상세 조회: content={}", webtoon);
        log.info("웹툰 상세 조회: id={}, title={}", webtoon.getId(), webtoon.getTitle());

        // episodeDtos를 만드는 로직은 그대로 둡니다.
        List<EpisodeDto> episodeDtos = webtoon.getEpisodes().stream()
                .map(EpisodeDto::new)
                .collect(Collectors.toList());

        // Entity를 ContentDetailDto로 변환하여 반환합니다.
        return new WebtoonDetailDto(webtoon, episodeDtos);
    }




// =====================================================================================================================

// 웹툰 썸네일 이미지 업로드
/*
    @Transactional  // '쓰기' 메소드: 클래스 설정을 덮어쓰고 readOnly=false인 기본 트랜잭션 적용
    public void createWebtoon(WebtoonCreateRequestDto requestDto, MultipartFile thumbnailFile) throws IOException {
        // 1. 이미지 파일 처리
        String thumbnailUrl = imageUploadService.storeFile(thumbnailFile);

        // 2. DTO와 이미지 URL을 바탕으로 Entity 생성
        Content content = Content.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .thumbnailUrl(thumbnailUrl) // 실제 저장된 이미지 경로를 사용
                .category(requestDto.getCategory())
                .build();

        // 3. 데이터베이스에 저장
        webtoonRepository.save(content);
    }
*/
}
