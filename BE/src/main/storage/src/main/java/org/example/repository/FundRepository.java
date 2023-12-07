package org.example.repository;

import org.example.entity.FundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundRepository extends JpaRepository<FundEntity, Long> {
    Optional<FundEntity> findByEmail(String email);

}
