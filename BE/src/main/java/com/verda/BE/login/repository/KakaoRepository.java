package com.verda.BE.login.repository;

import com.verda.BE.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoRepository extends JpaRepository<UserEntity, Long> {
}
