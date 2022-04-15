package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.ColorMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorMetadataRepository extends JpaRepository<ColorMetadataModel, Long> {
    public ColorMetadataModel findByJobid(Long jobid);
}
