package com.be.prac_toon.content.repository;

import com.be.prac_toon.content.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * EpisodeRepository 인터페이스는 JPA를 사용하여 Episode 엔티티에 대한 CRUD 작업을 수행하는 리포지토리입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 메소드와 페이징, 정렬 기능을 제공합니다.
 */
public interface EpisodeRepository extends JpaRepository<Episode, Long> {

}
