package com.verda.BE.board.repository;

import com.verda.BE.board.entity.UserPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<UserPostEntity, Long> {
//    List<UserPostEntity> findAllByOrderByPostIdDesc();
//    게시물 전체조회
    List<UserPostEntity> findAllByOrderByCreatedAtDesc();

    Page<UserPostEntity> findByPostIdLessThanOrderByPostIdDesc(Long lastPostId, PageRequest pageRequest);

    Slice<UserPostEntity> findByUserEntityUserId(long userid,Pageable pageable);
}
