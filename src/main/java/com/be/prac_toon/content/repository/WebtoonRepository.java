package com.be.prac_toon.content.repository;

import com.be.prac_toon.content.domain.Content;
import com.be.prac_toon.content.domain.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

// JpaRepository를 상속받으며, 첫 번째 제네릭에는 Entity 클래스, 두 번째에는 ID 필드의 타입을 지정합니다.
public interface WebtoonRepository extends JpaRepository<Content, Long> {

    /**
     *  연재 요일(serializationDay)을 기준으로 웹툰 목록을 찾는 메소드
     *  @Query: JPQL 쿼리를 직접 작성
     *  MEMBER OF: :dayOfWeek 파라미터가 c.serializationDays 컬렉션의 멤버(원소)인지 확인
    */
    @Query("SELECT c FROM Content c WHERE :dayOfWeek MEMBER OF c.serializationDays")
    List<Content> findBySerializationDay(@Param("dayOfWeek") DayOfWeek dayOfWeek);

    /**
     * 특정 시간 이후에 새로운 에피소드가 등록된 콘텐츠 목록을 조회합니다.
     * @param startDate 기준 시간 (예: 24시간 전)
     * @return 최근 업데이트된 콘텐츠 목록
     */
    @Query("SELECT DISTINCT e.content FROM Episode e WHERE e.createdAt >= :startDate ORDER BY e.createdAt DESC")
    List<Content> findRecentlyUpdatedContent(@Param("startDate") Instant startDate);


    // 조회수(viewCount)를 기준으로 내림차순(Desc) 정렬하여 상위 5개(Top5)를 조회
    // JPA가 메소드 이름을 분석하여 ORDER BY view_count DESC LIMIT 5 이라는 SQL을 자동으로 생성
    List<Content> findTop5ByOrderByViewCountDesc();


}
