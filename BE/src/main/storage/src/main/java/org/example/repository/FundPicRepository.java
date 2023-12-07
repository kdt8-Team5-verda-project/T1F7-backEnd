package org.example.repository;

import org.example.entity.FundPicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundPicRepository extends JpaRepository<FundPicEntity, Long> {
    Optional<FundPicEntity> findByFundEntityFmId(Long fmId);
}
