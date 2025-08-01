package com.be.prac_toon.content.service;

import com.be.prac_toon.content.domain.*;
import com.be.prac_toon.content.dto.EpisodeViewerDto;
import com.be.prac_toon.content.dto.WebtoonListDto;
import com.be.prac_toon.content.dto.WebtoonDetailDto;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

//@Slf4j
//@Service                        // 이 클래스가 비즈니스 로직을 처리하는 서비스 계층임을 나타냅니다.
//@Transactional(readOnly = true) // 클래스 레벨: 모든 public 메소드에 기본으로 '읽기 전용' 적용
//@RequiredArgsConstructor        // final로 선언된 필드에 대한 생성자를 자동으로 만들어줍니다. (생성자 주입)
//public class ContentService {
//
//    private final ImageUploadService imageUploadService;    // 이미지 업로드 서비스 의존성 주입
//    private final ContentRepository contentRepository;      // 웹툰 데이터베이스 레포지토리 의존성 주입
//    private final EpisodeRepository episodeRepository;      // 에피소드 데이터베이스 레포지토리 의존성 주입
//
//// 추천 웹툰(메인 배너용)을 조회하는 메소드
//    public Optional<WebtoonListDto> getFeaturedWebtoon() {
//
//    // 조회 목록 중 랜덤으로 하나를 선택하는 로직(임시) =========
//        // 1. 'today' 카테고리에 해당하는 웹툰 목록을 DB에서 조회
//        List<Content> contents = contentRepository.findTop5ByOrderByViewCountDesc();
//
//        // 2. 추천 웹툰이 없을 경우 Optional.empty() 반환
//        if (contents.isEmpty()) {
//            return Optional.empty();
//        }
//
//        // 3. 0부터 (리스트 크기 - 1) 사이의 랜덤한 인덱스를 생성
//        int randomIndex = new Random().nextInt(contents.size());
//
//        // 4. 랜덤 인덱스를 사용해 리스트에서 웹툰 하나를 선택
//        Content randomContent = contents.get(randomIndex);
//
//        // 5. 선택된 웹툰을 DTO로 변환하여 Optional로 감싸서 반환
//        return Optional.of(new WebtoonListDto(
//                randomContent.getId(),
//                randomContent.getTitle(),
//                randomContent.getAuthor(),
//                randomContent.getThumbnailUrl())
//        );
//    }
//
//// 메인 > '오늘의 업데이트' 웹툰 목록을 조회하는 메소드
//    public List<WebtoonListDto> getTodayWebtoons() {
//
//        // 'today' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
////        List<Content> contents = webtoonRepository.findByCategory("today");
//
//    // =================================================================================================================
//
//        // 1. Java 표준 라이브러리를 사용해 Enum타입으로 오늘 날짜의 요일을 가져옵니다 (예: MONDAY, TUESDAY...).
//        java.time.DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
//        log.info("오늘의 요일: {}", currentDay);
//
//        // 2. Java의 DayOfWeek를 우리가 만든 Enum으로 변환합니다.
//        DayOfWeek todayEnum = DayOfWeek.valueOf(currentDay.name());
//        log.info("오늘의 요일 Enum: {}", todayEnum);
//
//        // 3. 해당 요일의 웹툰들을 DB에서 조회합니다.
//        List<Content> contents = contentRepository.findBySerializationDay(todayEnum);
//
//    // =================================================================================================================
//        // 추후 실제 웹툰 연재 등록 시 업데이트 목록에 뜰 수 있도록 하는 로직
//        // 1. 현재 시간으로부터 24시간 전 시점을 계산합니다.
////        Instant twentyFourHoursAgo = Instant.now().minus(24, ChronoUnit.HOURS);
//
//        // 2. 24시간 이내에 에피소드가 등록된 콘텐츠 목록을 DB에서 조회합니다.
////        List<Content> webtoons = webtoonRepository.findRecentlyUpdatedContent(twentyFourHoursAgo);
//    // =================================================================================================================
//
//        // 조회된 Entity 목록을 DTO 목록으로 변환하여 반환합니다.
//        return contents.stream()
//                .map(content ->
//                        new WebtoonListDto(
//                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
//                        )
//                )
//                .collect(Collectors.toList());
//    }
//
//// 메인 > '인기 TOP 10' 웹툰 목록을 조회하는 메소드
//    public List<WebtoonListDto> getPopularWebtoons() {
//
//        // 'popular' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
////        List<Content> contents = webtoonRepository.findByCategory("popular");
//
//    // =================================================================================================================
//        List<Content> contents = contentRepository.findTop5ByOrderByViewCountDesc();
//
//        // DTO 목록으로 변환하여 반환합니다.
//        return contents.stream()
//                .map(content ->
//                        new WebtoonListDto(
//                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
//                        )
//                )
//                .collect(Collectors.toList());
//    }
//
//// =====================================================================================================================
//
//    // 웹툰 상세 정보 조회 메소드
//    public WebtoonDetailDto getWebtoonDetail(Long webtoonId) {
//
//        // ID로 웹툰을 찾고, 없으면 예외를 발생시킵니다.
//        Content content = contentRepository.findById(webtoonId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 웹툰을 찾을 수 없습니다. id=" + webtoonId));
//
//        log.info("웹툰 상세 조회: content={}", content);
//        log.info("웹툰 상세 조회: id={}, title={}", content.getId(), content.getTitle());
//
//        // episodeDtos를 만드는 로직은 그대로 둡니다.
//        List<EpisodeDto> episodeDtos = content.getEpisodes().stream()
//                .map(EpisodeDto::new)
//                .collect(Collectors.toList());
//
//        // Entity를 ContentDetailDto로 변환하여 반환합니다.
//        return new WebtoonDetailDto(content, episodeDtos);
//    }
//
//    // 웹툰 뷰어 조회 메소드
//    public EpisodeViewerDto getEpisodeViewer(Long episodeId) {
//        // 1. 요청된 에피소드를 DB에서 찾음
//        Episode currentEpisode = episodeRepository.findById(episodeId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 에피소드를 찾을 수 없습니다. id=" + episodeId));
//
//        // 2. 부모 웹툰(Content)을 찾고, 해당 웹툰의 모든 에피소드를 회차순으로 정렬
//        Content parentContent = currentEpisode.getContent();
//        List<Episode> allEpisodes = parentContent.getEpisodes().stream()
//                .sorted(Comparator.comparingInt(Episode::getEpisodeNumber))
//                .toList();
//
//        // 3. 현재 에피소드의 인덱스를 찾아 이전/다음 에피소드 ID 계산
//        int currentIndex = allEpisodes.indexOf(currentEpisode);
//        Long prevEpisodeId = (currentIndex > 0) ? allEpisodes.get(currentIndex - 1).getEpisodeId() : null;
//        Long nextEpisodeId = (currentIndex < allEpisodes.size() - 1) ? allEpisodes.get(currentIndex + 1).getEpisodeId() : null;
//
//        /*
//            4. 현재 에피소드의 이미지 URL 목록을 순서대로 정렬하여 가져옴
//                1. currentEpisode.getEpisodeImages():
//                    먼저, 현재 Episode 객체에 속해있는 List<EpisodeImage>를 가져옵니다.
//                    이 리스트 안에는 순서가 뒤죽박죽일 수 있는 EpisodeImage 객체들이 여러 개 들어있습니다.
//                    각 객체는 episodeImageId, imageUrl, imageOrder 등의 정보를 모두 담고 있습니다.
//                2. stream(): 이 원재료 덩어리 리스트를 스트림(Stream)이라는 특별한 데이터 흐름으로 변환합니다. 이제 EpisodeImage 객체들이 컨베이어 벨트 위를 하나씩 흘러갈 준비를 마쳤습니다.
//                3. sorted(...): 컨베이어 벨트 위를 흐르는 객체들을 정렬
//                4. Comparator.comparingInt(...): "숫자(정수)를 기준으로 비교해서 정렬하겠다"고 알려줍니다.
//                    4-1. EpisodeImage::getImageOrder:
//                        **메소드 참조(Method Reference)**라는 문법입니다.
//                        episodeImage -> episodeImage.getImageOrder() 와 동일한 의미입니다.
//                        즉, "컨베이어 벨트를 지나는 각 EpisodeImage 객체에서, getImageOrder() 메소드를 호출하여
//                        그 결과(이미지 순서 번호 - 오름차순)를 정렬 기준으로 삼아라" 라는 뜻
//                5. map(...): 정렬된 EpisodeImage 객체들을 하나씩 꺼내서, 그 객체의 imageUrl만 추출합니다.
//                    5-1. EpisodeImage::getImageUrl:
//                        또 다른 메소드 참조입니다.
//                        episodeImage -> episodeImage.getImageUrl() 와 동일한 의미로,
//                        "각 EpisodeImage 객체에서 imageUrl만 꺼내서 새로운 스트림을 만들어라" 라는 뜻
//                6. collect(Collectors.toList()):
//                    마지막으로, 정렬된 imageUrl들을 다시 List<String> 형태로 모읍니다.
//                    이 단계에서 스트림이 끝나고, 최종적으로 정렬된 이미지 URL 리스트가 만들어집니다.
//
//            Result:
//                최종적으로, currentEpisode에 속한 EpisodeImage 객체들을 imageOrder 순서대로 정렬하고,
//                각 객체의 imageUrl만 추출하여 List<String> 형태로 반환합니다.
//                이 리스트는 뷰어에서 에피소드 이미지를 표시할 때 사용됩니다.
//         */
//        List<String> imageUrls = currentEpisode.getEpisodeImages().stream()
//                .sorted(Comparator.comparingInt(EpisodeImage::getImageOrder))
//                .map(EpisodeImage::getImageUrl)
//                .collect(Collectors.toList());
//
//        // 5. 최종 뷰어 DTO를 만들어 반환
//        return new EpisodeViewerDto(
//                currentEpisode.getEpisodeId(),
//                parentContent.getTitle(),
//                currentEpisode.getEpisodeNumber() + "화. " + currentEpisode.getTitle(),
//                imageUrls,
//                prevEpisodeId,
//                nextEpisodeId
//        );
//    }
//
//
//// =====================================================================================================================
//
//// 웹툰 썸네일 이미지 업로드
///*
//    @Transactional  // '쓰기' 메소드: 클래스 설정을 덮어쓰고 readOnly=false인 기본 트랜잭션 적용
//    public void createWebtoon(WebtoonCreateRequestDto requestDto, MultipartFile thumbnailFile) throws IOException {
//        // 1. 이미지 파일 처리
//        String thumbnailUrl = imageUploadService.storeFile(thumbnailFile);
//
//        // 2. DTO와 이미지 URL을 바탕으로 Entity 생성
//        Content content = Content.builder()
//                .title(requestDto.getTitle())
//                .author(requestDto.getAuthor())
//                .thumbnailUrl(thumbnailUrl) // 실제 저장된 이미지 경로를 사용
//                .category(requestDto.getCategory())
//                .build();
//
//        // 3. 데이터베이스에 저장
//        webtoonRepository.save(content);
//    }
//*/
//}

public interface ContentService {
    // 이 서비스가 어떤 타입을 지원하는지 알려주는 메소드
    ContentType getSupportContentType();

    // 메인 페이지용 API 로직
    Optional<WebtoonListDto> getFeaturedContent();
    List<WebtoonListDto> getTodayContent();
    List<WebtoonListDto> getPopularContent();

    // 상세 및 뷰어 페이지용 API 로직
    WebtoonDetailDto getContentDetail(Long contentId);
    EpisodeViewerDto getEpisodeViewer(Long episodeId, Long currentUserId);
}
