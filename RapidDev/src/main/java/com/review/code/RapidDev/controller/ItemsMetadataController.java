package com.review.code.RapidDev.controller;

import com.review.code.RapidDev.repository.ItemMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.review.code.RapidDev.service.ItemsMetaDataService;
import com.review.code.RapidDev.service.AttributesMetadataService;
import com.review.code.RapidDev.model.ItemsMetadataModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

//TODO - need to add code to populate service facade from the developer system , as of not only model name and import is being done
@RestController
public class ItemsMetadataController {
    @Autowired
    ItemsMetaDataService itemsService;

    @Autowired
    AttributesMetadataService attributesService;
    @Value("${hybrisdeveloper.datasource.url}")
    private String dbURl = "";
    @Value("${hybrisdeveloper.datasource.username}")
    private String user = "";
    @Value("${hybrisdeveloper.datasource.password}")
    private String password = "";
    private static String FILTER_TO_SELECT_ENUM = "de.hybris.platform.jalo.enumeration.EnumerationValue";
    @Autowired
    ItemMetadataRepository itemsMetaDataRepository;

    @RequestMapping("updateItemsMetaData")
    public ResponseEntity updateItemsMetaData() throws IOException {

        PreparedStatement statement;
        Connection con;

        try {
            //  Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection(dbURl , user, password);
            List<ItemsMetadataModel> items = itemsMetaDataRepository.findAll();

            //Get master item
            ItemsMetadataModel masterItem = items.get(0);
            for (ItemsMetadataModel item : items) {
                attributesService.insertAttributes(item.getItemmodelname() , con);
            }
            ;
            attributesService.flush();
            con.close();

        } catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity("problem while updating attributes from developer's hybris db" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return itemsService.walkDirectory();

    }
/*
    @RequestMapping("showItemsMetaData")
    public List<ItemsMetadataModel> showItemsMetaData() {
        List<ItemsMetadataModel> itemsList = itemsService.getAllItems();
        return itemsList;
    }

    @RequestMapping("showAttributesForModel")
    public List<AttributesMetadataModel> showAttributesForModel(@RequestParam("modelName") String modelName) {
        List<AttributesMetadataModel> attributeList = attributesService.getAttributeForModel(modelName);
        return attributeList;
    }

    @RequestMapping("populateAttributeMetaData")
    public ResponseEntity populateAttributeMetaData() {
        return itemsService.streamItemsMetaDataModel();
    }


    @PostMapping(path = "updateItemMetadata", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateItemMetadata(@NonNull @RequestBody String payload) throws Exception {
        try {
            final JSONObject obj = new JSONObject(payload);

            return itemsService.updateItemsMetaDataModel(obj);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_MODIFIED);

        }
    }

    @DeleteMapping("/itemmetadata/{modelName}")
    public ResponseEntity deleteEmployee(@PathVariable String modelName) {
        return itemsService.deleteByname(modelName);
    }*/
}