package com.verda.BE.s3.repository;

import com.verda.BE.s3.Entity.FundPicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundPicRepository extends JpaRepository<FundPicEntity, Long> {
    Optional<FundPicEntity> findByFundEntityFmId(Long fmId);
}
