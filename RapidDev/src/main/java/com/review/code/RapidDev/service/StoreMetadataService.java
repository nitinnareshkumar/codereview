package com.review.code.RapidDev.service;

import com.review.code.RapidDev.repository.StoreMetadataRepository;
import com.review.code.RapidDev.model.StoreMetadataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreMetadataService {
    @Autowired
    StoreMetadataRepository storeMetadataRepository;

    public void saveModel(StoreMetadataModel storeMetadataModel){
        storeMetadataRepository.save(storeMetadataModel);
    }

/*    public void saveRelations(Relations relationPojo){
        RelationModel relation = convertRelation(relationPojo);
        relationRepository.save(relation);
    }

    public Optional<RelationModel> getRelationById(Long Id){
        return relationRepository.findById(Id);
    }
*/
    public StoreMetadataModel getStoreMetadataByJobid(Long jobid){
        return storeMetadataRepository.findByJobid(jobid);
    }

    /*private RelationModel convertRelation(Relations relationPojo){
        RelationModel relation = new RelationModel();
        relation.setSourcetype(relationPojo.getSource().getType());
        relation.setSourcequalifier(relationPojo.getSource().getQualifier());
        relation.setSourcecardinality(relationPojo.getSource().getCardinality());
        // target attributes
        relation.setTargettype(relationPojo.getTarget().getType());
        relation.setTargetqualifier(relationPojo.getTarget().getQualifier());
        relation.setTargetcardinality(relationPojo.getTarget().getCardinality());
        return relation;
    }*/
}
