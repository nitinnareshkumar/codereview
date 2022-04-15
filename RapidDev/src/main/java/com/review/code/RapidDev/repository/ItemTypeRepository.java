package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.ItemsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemsModel, Long> {
    public ItemsModel findAllByJobid(Long jobid);
}
