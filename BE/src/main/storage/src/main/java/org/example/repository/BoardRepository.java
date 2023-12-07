package org.example.repository;

import org.example.entity.UserPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<UserPostEntity, Long> {
//    List<UserPostEntity> findAllByOrderByPostIdDesc();
//    게시물 전체조회
    List<UserPostEntity> findAllByOrderByCreatedAtDesc();

    Page<UserPostEntity> findByPostIdLessThanOrderByPostIdDesc(Long lastPostId, PageRequest pageRequest);

    List<UserPostEntity> findByUserEntityUserId(long userid);
    
}
