package com.be.prac_toon.content.service;

import com.be.prac_toon.content.domain.Content;
import com.be.prac_toon.content.domain.ContentType;
import com.be.prac_toon.content.repository.ContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component                  // 이 클래스가 스프링 컴포넌트임을 나타냅니다.
public class ContentServiceFactory {

    private final Map<ContentType, ContentService> serviceMap;
    private final ContentRepository contentRepository; // 콘텐츠 타입을 조회하기 위해 주입

    /*
        의존성 주입에 사용해야 할 생성자 주입을 직접 만들고 이를 통해 ContentService 타입의 모든 빈을 Map으로 저장
        Spring이 시작될 때, ContentService 타입의 모든 빈(WebtoonService 등)을 주입받아 Map을 생성

        1. "일단 재료를 모두 받는다" (List<ContentService> services):
            Spring에게 'ContentService 인터페이스를 구현한 모든 빈(WebtoonService, VideoService 등)을 List 형태로 모두 모아서 줘!' 라고 요청합니다.
            Spring은 이 요청을 아주 잘 이해하고 실행해 줍니다. 특정 타입의 빈들을 모두 모아 리스트로 주입하는 것은 Spring의 핵심 기능 중 하나입니다.

        2. "생성자 안에서 재료를 가공한다" (stream().collect(...)):
            그렇게 전달받은 services 리스트를 가지고, 생성자 내부에서 직접 stream을 사용하여 우리가 원하는 Map<ContentType, ContentService> 형태로 가공합니다.
            getSupportContentType() 메소드를 호출하여 각 서비스의 타입을 Map의 Key로 사용하고, 서비스 객체 자체를 Value로 사용하여 Map을 완성합니다.
     */
    public ContentServiceFactory(List<ContentService> services, ContentRepository contentRepository) {
        this.serviceMap = services.stream()
                .collect(Collectors.toUnmodifiableMap(ContentService::getSupportContentType, Function.identity()));
        this.contentRepository = contentRepository;
    }

    // ID를 기반으로 콘텐츠 타입을 DB에서 조회하는 헬퍼 메소드
    public ContentType getContentTypeById(Long contentId) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("콘텐츠를 찾을 수 없습니다. id=" + contentId));
        log.info("ContentServiceFactory|getContentTypeById| 콘텐츠 조회: id={}, 타입={}", contentId, content.getContentType());

        return content.getContentType();
    }

    // 콘텐츠 타입을 기반으로 적절한 서비스를 반환
    public ContentService getService(ContentType contentType) {

        ContentService service = serviceMap.get(contentType);

        log.info("ContentServiceFactory|getService| 콘텐츠 타입: {}, 서비스: {}", contentType, service);

        if (service == null) {
            throw new IllegalArgumentException("지원하지 않는 콘텐츠 타입입니다: " + contentType);
        }

        return service;
    }

}
