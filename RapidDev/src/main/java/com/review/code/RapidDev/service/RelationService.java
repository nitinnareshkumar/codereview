package com.review.code.RapidDev.service;

import com.review.code.RapidDev.pojo.item.Relations;
import com.review.code.RapidDev.repository.RelationRepository;
import com.review.code.RapidDev.model.RelationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationService {
    @Autowired
    RelationRepository relationRepository;

    public void saveRelations(Relations relationPojo){
        RelationModel relation = convertRelation(relationPojo);
        relationRepository.save(relation);
    }

    public void saveModel(RelationModel relationModel){
        relationRepository.save(relationModel);
    }
    public Optional<RelationModel> getRelationById(Long Id){
        return relationRepository.findById(Id);
    }

    public List<RelationModel> getRelationByJobid(Long jobid){
        return relationRepository.findAllByJobid(jobid);
    }

    private RelationModel convertRelation(Relations relationPojo){
        RelationModel relation = new RelationModel();
        relation.setSourcetype(relationPojo.getSource().getType());
        relation.setSourcequalifier(relationPojo.getSource().getQualifier());
        relation.setSourcecardinality(relationPojo.getSource().getCardinality());
        // target attributes
        relation.setTargettype(relationPojo.getTarget().getType());
        relation.setTargetqualifier(relationPojo.getTarget().getQualifier());
        relation.setTargetcardinality(relationPojo.getTarget().getCardinality());
        return relation;
    }
}
