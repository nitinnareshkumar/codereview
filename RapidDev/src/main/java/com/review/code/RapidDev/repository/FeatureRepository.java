package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.FeatureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureModel, Long> {
    public List< FeatureModel> findAllByJobid(Long jobid);
}
