package com.verda.BE.board.repository;

import com.verda.BE.board.entity.UserPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<UserPostEntity, Long> {
//    List<UserPostEntity> findAllByOrderByPostIdDesc();
    List<UserPostEntity> findAllByOrderByCreatedAtDesc();
}
