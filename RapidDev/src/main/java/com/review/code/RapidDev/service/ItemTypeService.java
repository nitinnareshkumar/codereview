package com.review.code.RapidDev.service;

import com.review.code.RapidDev.repository.ItemMetadataRepository;
import com.review.code.RapidDev.repository.ItemTypeRepository;
import com.review.code.RapidDev.model.ItemsMetadataModel;
import com.review.code.RapidDev.model.ItemsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTypeService {
    @Autowired
    ItemTypeRepository itemTypeRepository;

    @Autowired
    ItemMetadataRepository itemMetadataRepository;

    public void saveModel(ItemsModel itemsModel){
        itemTypeRepository.save(itemsModel);
    }

    public ItemsModel getItemsByJobid(Long jobid){
        return itemTypeRepository.findAllByJobid(jobid);
    }

    public ItemsMetadataModel getItemsMetadataByItemCode(String itemCode){
        return itemMetadataRepository.findByItemmodelname(itemCode);
    }
}
