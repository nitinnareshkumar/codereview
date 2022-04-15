package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.ItemsMetadataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMetadataRepository extends JpaRepository<ItemsMetadataModel, String> {
    ItemsMetadataModel findByItemmodelname(String itemCode);
}
