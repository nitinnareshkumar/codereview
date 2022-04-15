package com.review.code.RapidDev.service;

import com.review.code.RapidDev.repository.ColorMetadataRepository;
import com.review.code.RapidDev.model.ColorMetadataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorMetadataService {
    @Autowired
    ColorMetadataRepository colorMetadataRepository;

    public void saveModel(ColorMetadataModel colorMetadataModel){
        colorMetadataRepository.save(colorMetadataModel);
    }


    public ColorMetadataModel getColorMetadataByJobid(Long jobid){
        return colorMetadataRepository.findByJobid(jobid);
    }


}
