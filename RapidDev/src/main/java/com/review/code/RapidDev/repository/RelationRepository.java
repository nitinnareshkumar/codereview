package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.RelationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<RelationModel, Long> {
    public List< RelationModel> findAllByJobid(Long jobid);
}
