package com.be.prac_toon.content.service;

import com.be.prac_toon.content.domain.*;
import com.be.prac_toon.content.dto.EpisodeDto;
import com.be.prac_toon.content.dto.EpisodeViewerDto;
import com.be.prac_toon.content.dto.WebtoonDetailDto;
import com.be.prac_toon.content.dto.WebtoonListDto;
import com.be.prac_toon.content.repository.ContentRepository;
import com.be.prac_toon.content.repository.EpisodeLikeRepository;
import com.be.prac_toon.content.repository.EpisodeRepository;
import com.be.prac_toon.user.domain.User;
import com.be.prac_toon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor        // final로 선언된 필드에 대한 생성자를 자동으로 만들어줍니다. (생성자 주입)
public class WebtoonService implements ContentService {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final EpisodeRepository episodeRepository;
    private final EpisodeLikeRepository episodeLikeRepository;

    @Override
    public ContentType getSupportContentType() {
        return ContentType.WEBTOON;
    }

    // 추천 웹툰(메인 배너용)을 조회하는 메소드
    @Override
    public Optional<WebtoonListDto> getFeaturedContent() {

        // 조회 목록 중 랜덤으로 하나를 선택하는 로직(임시) =========
        // 1. 'today' 카테고리에 해당하는 웹툰 목록을 DB에서 조회
        List<Content> contents = contentRepository.findTop5ByOrderByViewCountDesc();

        // 2. 추천 웹툰이 없을 경우 Optional.empty() 반환
        if (contents.isEmpty()) {
            return Optional.empty();
        }

        // 3. 0부터 (리스트 크기 - 1) 사이의 랜덤한 인덱스를 생성
        int randomIndex = new Random().nextInt(contents.size());

        // 4. 랜덤 인덱스를 사용해 리스트에서 웹툰 하나를 선택
        Content randomContent = contents.get(randomIndex);

        // 5. 선택된 웹툰을 DTO로 변환하여 Optional로 감싸서 반환
        return Optional.of(new WebtoonListDto(
                randomContent.getId(),
                randomContent.getTitle(),
                randomContent.getAuthor(),
                randomContent.getThumbnailUrl())
        );

    }

    // 오늘의 업데이트 웹툰 목록을 조회하는 메소드
    @Override
    public List<WebtoonListDto> getTodayContent() {
        // 1. Java 표준 라이브러리를 사용해 Enum타입으로 오늘 날짜의 요일을 가져옵니다 (예: MONDAY, TUESDAY...).
        java.time.DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        log.info("오늘의 요일: {}", currentDay);

        // 2. Java의 DayOfWeek를 우리가 만든 Enum으로 변환합니다.
        DayOfWeek todayEnum = DayOfWeek.valueOf(currentDay.name());
        log.info("오늘의 요일 Enum: {}", todayEnum);

        // 3. 해당 요일의 웹툰들을 DB에서 조회합니다.
        List<Content> contents = contentRepository.findBySerializationDay(todayEnum);

    // =================================================================================================================
        // 추후 실제 웹툰 연재 등록 시 업데이트 목록에 뜰 수 있도록 하는 로직
        // 1. 현재 시간으로부터 24시간 전 시점을 계산합니다.
//        Instant twentyFourHoursAgo = Instant.now().minus(24, ChronoUnit.HOURS);

        // 2. 24시간 이내에 에피소드가 등록된 콘텐츠 목록을 DB에서 조회합니다.
//        List<Content> webtoons = webtoonRepository.findRecentlyUpdatedContent(twentyFourHoursAgo);
    // =================================================================================================================

        // 조회된 Entity 목록을 DTO 목록으로 변환하여 반환합니다.
        return contents.stream()
                .map(content ->
                        new WebtoonListDto(
                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

    // 인기 TOP5 웹툰 목록을 조회하는 메소드
    @Override
    public List<WebtoonListDto> getPopularContent() {
        // 'popular' 카테고리에 해당하는 웹툰들을 DB에서 조회합니다.
        List<Content> contents = contentRepository.findTop5ByOrderByViewCountDesc();

        // DTO 목록으로 변환하여 반환합니다.
        return contents.stream()
                .map(content ->
                        new WebtoonListDto(
                            content.getId(), content.getTitle(), content.getAuthor(), content.getThumbnailUrl()
                        )
                )
                .collect(Collectors.toList());
    }

// ====================================================================================================================

    // 웹툰 상세 페이지 정보를 조회
    @Override
    public WebtoonDetailDto getContentDetail(Long contentId) {

        // ID로 웹툰을 찾고, 없으면 예외를 발생시킵니다.
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 웹툰을 찾을 수 없습니다. id=" + contentId));

        log.info("웹툰 상세 조회: content={}", content);
        log.info("웹툰 상세 조회: id={}, title={}", content.getId(), content.getTitle());

        // episodeDtos를 만드는 로직은 그대로 둡니다.
        List<EpisodeDto> episodeDtos = content.getEpisodes().stream()
                .map(EpisodeDto::new)
                .collect(Collectors.toList());

        // Entity를 ContentDetailDto로 변환하여 반환합니다.
        return new WebtoonDetailDto(content, episodeDtos);
    }

    // 에피소드 뷰어 정보(웹툰 이미지)를 조회
    @Override
    public EpisodeViewerDto getEpisodeViewer(Long episodeId, Long currentUserId) {

        // 1. 요청된 에피소드를 DB에서 찾음
        Episode currentEpisode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 에피소드를 찾을 수 없습니다. id=" + episodeId));

        // 2. 부모 웹툰(Content)을 찾고, 해당 웹툰의 모든 에피소드를 회차순으로 정렬
        Content parentContent = currentEpisode.getContent();
        List<Episode> allEpisodes = parentContent.getEpisodes().stream()
                .sorted(Comparator.comparingInt(Episode::getEpisodeNumber))
                .toList();

        // 3. 현재 에피소드의 인덱스를 찾아 이전/다음 에피소드 ID 계산
        int currentIndex = allEpisodes.indexOf(currentEpisode);
        Long prevEpisodeId = (currentIndex > 0) ? allEpisodes.get(currentIndex - 1).getEpisodeId() : null;
        Long nextEpisodeId = (currentIndex < allEpisodes.size() - 1) ? allEpisodes.get(currentIndex + 1).getEpisodeId() : null;

        /*
            4. 현재 에피소드의 이미지 URL 목록을 순서대로 정렬하여 가져옴
                1. currentEpisode.getEpisodeImages():
                    먼저, 현재 Episode 객체에 속해있는 List<EpisodeImage>를 가져옵니다.
                    이 리스트 안에는 순서가 뒤죽박죽일 수 있는 EpisodeImage 객체들이 여러 개 들어있습니다.
                    각 객체는 episodeImageId, imageUrl, imageOrder 등의 정보를 모두 담고 있습니다.
                2. stream(): 이 원재료 덩어리 리스트를 스트림(Stream)이라는 특별한 데이터 흐름으로 변환합니다. 이제 EpisodeImage 객체들이 컨베이어 벨트 위를 하나씩 흘러갈 준비를 마쳤습니다.
                3. sorted(...): 컨베이어 벨트 위를 흐르는 객체들을 정렬
                4. Comparator.comparingInt(...): "숫자(정수)를 기준으로 비교해서 정렬하겠다"고 알려줍니다.
                    4-1. EpisodeImage::getImageOrder:
                        **메소드 참조(Method Reference)**라는 문법입니다.
                        episodeImage -> episodeImage.getImageOrder() 와 동일한 의미입니다.
                        즉, "컨베이어 벨트를 지나는 각 EpisodeImage 객체에서, getImageOrder() 메소드를 호출하여
                        그 결과(이미지 순서 번호 - 오름차순)를 정렬 기준으로 삼아라" 라는 뜻
                5. map(...): 정렬된 EpisodeImage 객체들을 하나씩 꺼내서, 그 객체의 imageUrl만 추출합니다.
                    5-1. EpisodeImage::getImageUrl:
                        또 다른 메소드 참조입니다.
                        episodeImage -> episodeImage.getImageUrl() 와 동일한 의미로,
                        "각 EpisodeImage 객체에서 imageUrl만 꺼내서 새로운 스트림을 만들어라" 라는 뜻
                6. collect(Collectors.toList()):
                    마지막으로, 정렬된 imageUrl들을 다시 List<String> 형태로 모읍니다.
                    이 단계에서 스트림이 끝나고, 최종적으로 정렬된 이미지 URL 리스트가 만들어집니다.

            Result:
                최종적으로, currentEpisode에 속한 EpisodeImage 객체들을 imageOrder 순서대로 정렬하고,
                각 객체의 imageUrl만 추출하여 List<String> 형태로 반환합니다.
                이 리스트는 뷰어에서 에피소드 이미지를 표시할 때 사용됩니다.
         */
        List<String> imageUrls = currentEpisode.getEpisodeImages().stream()
                .sorted(Comparator.comparingInt(EpisodeImage::getImageOrder))
                .map(EpisodeImage::getImageUrl)
                .collect(Collectors.toList());

        // 현재 사용자가 이 에피소드에 좋아요를 눌렀는지 확인
        boolean isLikedByCurrentUser = false;

        // 1. currentUserId가 null이 아닐 때만 DB를 조회합니다.
        if (currentUserId != null) {

            // 2. 사용자도 DB에 실제로 존재하는지 확인합니다.
            userRepository.findById(currentUserId).ifPresent(user -> {
                // isLikedByCurrentUser는 user 변수를 직접 참조할 수 없어,
                // 아래처럼 별도의 변수에 결과를 할당하는 것이 일반적이지만,
                // 여기서는 바로 확인하고 결과를 boolean 변수에 할당하는 것이 더 간결합니다.
            });

            // ifPresent는 결과가 없을 때 아무것도 하지 않으므로, 아래처럼 별도로 처리합니다.
            isLikedByCurrentUser = userRepository.findById(currentUserId)
                    .flatMap(user -> episodeLikeRepository.findByUserAndEpisode(user, currentEpisode))
                    .isPresent();
        }

        // 5. 최종 뷰어 DTO를 만들어 반환
        return new EpisodeViewerDto(
                currentEpisode.getEpisodeId(),
                currentEpisode.getContent().getTitle(),
                currentEpisode.getEpisodeNumber() + "화. " + currentEpisode.getTitle(),
                imageUrls,
                prevEpisodeId,
                nextEpisodeId,
                currentEpisode.getLikeCount(), // 총 좋아요 수
                isLikedByCurrentUser           // 현재 사용자의 좋아요 여부
        );
    }
}
