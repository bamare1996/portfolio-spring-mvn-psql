package com.example.portfolio.repository;

import com.example.portfolio.model.CvMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvMetadataRepository extends JpaRepository<CvMetadata, Long> {
}
