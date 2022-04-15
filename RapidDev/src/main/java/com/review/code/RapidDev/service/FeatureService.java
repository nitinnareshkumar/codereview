package com.review.code.RapidDev.service;

import com.review.code.RapidDev.repository.FeatureRepository;
import com.review.code.RapidDev.model.FeatureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {
    @Autowired
    FeatureRepository featureRepository;



    public void saveModel(FeatureModel featureModel){
        featureRepository.save(featureModel);
    }
    public Optional<FeatureModel> getFeatureById(Long Id){
        return featureRepository.findById(Id);
    }

    public List<FeatureModel> getFeaturesByJobid(Long jobid){
        return featureRepository.findAllByJobid(jobid);
    }

}
