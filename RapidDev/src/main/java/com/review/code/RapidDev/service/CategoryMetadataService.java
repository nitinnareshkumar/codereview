package com.review.code.RapidDev.service;

import com.review.code.RapidDev.model.CategoryModel;
import com.review.code.RapidDev.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CategoryMetadataService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryModel saveModel(CategoryModel categoryModel){
        return categoryRepository.save(categoryModel);
    }

/*    public void saveRelations(Relations relationPojo){
        RelationModel relation = convertRelation(relationPojo);
        relationRepository.save(relation);
    }

    public Optional<RelationModel> getRelationById(Long Id){
        return relationRepository.findById(Id);
    }
*/
    public Set< CategoryModel> getCategoryMetadataByJobid(Long jobid){
        return categoryRepository.findByJobid(jobid);
    }
    public Set< CategoryModel> getCategoryMetadataByJobidAndIsClass(Long jobid, boolean isClassification){
        return categoryRepository.findByJobidAndIsClassification(jobid, isClassification);
    }
    public CategoryModel getCategoryMetadataById(long id){
        return categoryRepository.findById(id);
    }
    public CategoryModel getCategoryMetadataByNameJobId(String name , Long jobId){
        return categoryRepository.findByNameJobid(name, jobId);
    };
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
