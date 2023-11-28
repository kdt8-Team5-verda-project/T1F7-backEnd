package com.verda.BE.login.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundRepository extends JpaRepository<FundEntity, Long> {
    Optional<FundEntity> findByEmail(String email);

}
