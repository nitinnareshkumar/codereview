package com.review.code.RapidDev.service;

import com.review.code.RapidDev.repository.AttributesMetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.review.code.RapidDev.model.AttributesMetadataModel;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AttributesMetadataService {
    @Autowired
    AttributesMetadataRepository attributesRepository;
    Logger log = LoggerFactory.getLogger(AttributesMetadataService.class);

    public void insertAttributes(String itemModelName , Connection con) throws SQLException {

         PreparedStatement statement;

         String[] attribute = new String[4] ;

        String query = "SELECT item_t0.InternalCode as A, item_t1.QualifierInternal as B, " +
                "item_t2.internalcode as C, " +
                "item_t3.InternalCode as D FROM composedtypes item_t0 JOIN attributedescriptors item_t1 ON " +
                " item_t0.PK =  item_t1.OwnerPkString LEFT JOIN atomictypes AS item_t2 ON " +
                "item_t1.AttributeTypePK  =  item_t2.PK LEFT JOIN composedtypes AS item_t3 ON " +
                "item_t1.AttributeTypePK  =  item_t3.PK WHERE  item_t0.InternalCode  = ? " +
                " and item_t1.QualifierInternal not in (SELECT  " +
                "item_t1.QualifierInternal FROM composedtypes item_t0 JOIN attributedescriptors item_t1 ON  " +
                "item_t0.PK =  item_t1.OwnerPkString  WHERE  item_t0.InternalCode  = 'Item') ";

        try {

            statement = con.prepareStatement(query);
            statement.setString(1, itemModelName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                attribute [0] = rs.getString(1);
                attribute [1] = rs.getString(2);
                attribute [2] = rs.getString(3);
                attribute [3] = rs.getString(4);
                AttributesMetadataModel obj = new AttributesMetadataModel(attribute);
                if (checkIfExists(obj)) continue;
                log.info("trying to save model " + itemModelName + "attribute " +  attribute [1]);
                attributesRepository.saveAndFlush(obj);

            }
        } catch (Exception ex) {
            log.error("error while trying to save model " + itemModelName + "attribute " +  attribute [1]);
            throw ex;
        }



    }

    public List<AttributesMetadataModel> getAttributeForModel(String modelName) {
        return attributesRepository.getAttributeByModel(modelName);
    }

    public void flush() {
        attributesRepository.flush();
    }

    boolean checkIfExists(AttributesMetadataModel obj) {
        if (obj.getAttributetype() == null)
            return attributesRepository.checkIfExists(obj.getItemmodelname(), obj.getAttributename()) == BigInteger.ONE;
        else
            return attributesRepository.checkIfExists(obj.getItemmodelname(), obj.getAttributename(), obj.getAttributetype()) == BigInteger.ONE;
    }
}