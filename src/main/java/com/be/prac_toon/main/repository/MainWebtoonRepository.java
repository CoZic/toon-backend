package com.be.prac_toon.main.repository;

import com.be.prac_toon.main.domain.MainWebtoonList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 상속받으며, 첫 번째 제네릭에는 Entity 클래스, 두 번째에는 ID 필드의 타입을 지정합니다.
public interface MainWebtoonRepository extends JpaRepository<MainWebtoonList, Long> {

    // 'category' 필드를 기준으로 웹툰 목록을 찾는 메소드를 정의합니다.
    // Spring Data JPA가 메소드 이름을 분석해서 자동으로 SQL 쿼리를 생성해 줍니다.
    // "FindBy" + "필드이름" 규칙을 따릅니다.
    List<MainWebtoonList> findByCategory(String category);

}
