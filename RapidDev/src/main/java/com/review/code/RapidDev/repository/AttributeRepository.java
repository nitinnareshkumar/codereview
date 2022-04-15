package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.AttributesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<AttributesModel, Long> {
}
