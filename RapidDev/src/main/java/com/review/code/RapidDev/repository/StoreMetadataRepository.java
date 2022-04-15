package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.StoreMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMetadataRepository extends JpaRepository<StoreMetadataModel, Long> {
    public StoreMetadataModel findByJobid(Long jobid);
}
