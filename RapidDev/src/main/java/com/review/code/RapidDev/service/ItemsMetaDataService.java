package com.review.code.RapidDev.service;


import com.review.code.RapidDev.exception.ItemException;
import com.review.code.RapidDev.model.ItemsMetadataModel;
import com.review.code.RapidDev.model.ItemsModel;
import com.review.code.RapidDev.model.RelationModel;
import com.review.code.RapidDev.pojo.item.Relations;
import com.review.code.RapidDev.repository.ItemMetadataRepository;
import com.review.code.RapidDev.event.AfterItemsXMLCreationEvent;
import com.review.code.RapidDev.utils.FileWriterUtil;
import com.review.code.RapidDev.utils.ItemsXmlUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONArray;

@Service
@PropertySource("classpath:application.properties")
public class ItemsMetaDataService {
    @Autowired
    ItemMetadataRepository itemsMetaDataRepository;
    @Autowired
    AttributesMetadataService attributesService;
    @Autowired
    private ItemsXmlUtil itemsXmlUtil;
    @Autowired
    private Environment env;
    @Autowired
    FileWriterUtil fileWriterUtil;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    ContextGeneratorUtil  contextGeneratorUtil;
    @Value("${folder.path}")
    private String folderPath = "";
    @Value("${hybrisdeveloper.datasource.url}")
    private String dbURl = "";
    @Value("${hybrisdeveloper.datasource.username}")
    private String user = "";
    @Value("${hybrisdeveloper.datasource.password}")

    private String password = "";
    private static String FILTER_TO_SELECT_ENUM = "de.hybris.platform.jalo.enumeration.EnumerationValue";



    Logger log = LoggerFactory.getLogger(ItemsMetaDataService.class);

    public void saveObject(ItemsMetadataModel m) {
        boolean b = itemsMetaDataRepository.existsById(m.getItemmodelname());
        if (b == false) {
            itemsMetaDataRepository.save(m);
        }
    }

    public List<ItemsMetadataModel> getAllItems() {
        return itemsMetaDataRepository.findAll();
    }

    public ItemsMetadataModel getItemsMetadataModelbyName(String ItemsMetadataModel) {
        return itemsMetaDataRepository.findByItemmodelname(ItemsMetadataModel);
    }
    public void saveModel(ItemsMetadataModel itemsMetadataModel){
        itemsMetaDataRepository.save(itemsMetadataModel);
    }

    public ResponseEntity walkDirectory() throws IOException {

        //todo single principle
        //exception handling--ok
        //lot of business logic in controller--ok
        //null pointer-ok
        //cyclomatic-ok
        //interface driven -- important
        //for loop - object create
        //db config coming from properties files--important
        try {
            Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!Files.isDirectory(file) && file.toString().endsWith("Model.java")) {
                        classDecompiler(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
//            logic to add enum in the itemmeta data table
            pupoluateEnum();
            String payload="test";//test payload
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();
            List<Relations> relationsList = new ArrayList<>();
            VelocityContext context = new VelocityContext();
            log.info("payload is " + payload);
            log.info("starting parsing json object");
            // Define the variable to hold the new itemtype and relation object --for POC only an instance ..
            ItemsModel itemsModel = new ItemsModel();
            Long jobid = System.currentTimeMillis();
            try {
                final JSONObject obj = new JSONObject(payload);
                JSONArray relationArray ;
                //Get the root node and parse the following objects - enumtypes, relations, typegroups or itemTypes
                final JSONArray type = obj.getJSONArray("Type");
                for (int i = 0; i < type.length(); i++) {
                    if (!((JSONObject) type.get(i)).isNull("header") ) {
                        itemsModel = itemsXmlUtil.getItemModelFromJSONObject(type);
                        context.put("itemList",itemsXmlUtil.getItemListFromJSONObject(type));
                    } else if (!((JSONObject) type.get(i)).isNull("relations")) {
                        relationArray =((JSONObject)type.get(i)).getJSONArray("relations");
                        for(int j=0; j<relationArray.length();j++){
                            RelationModel relationModel = new RelationModel();
                            relationModel = itemsXmlUtil.populateRelationModel(((JSONObject)relationArray.get(j)).getJSONArray("relationRow"));
                            itemsXmlUtil.saveRelation(relationModel,jobid);
                            relationsList.add(itemsXmlUtil.getRelationFromJSONObject(((JSONObject)relationArray.get(j)).getJSONArray("relationRow")));
                        }
                    }
                }
                // save the object in DB
                if(itemsModel.getOperation()== null){
                    itemsModel.setOperation("newitem_crud");
                }
                itemsXmlUtil.saveItem(itemsModel,jobid);
                System.out.println(itemsModel.getCode());
                //Add the datapoints in the velocity context

                context.put("relationList", relationsList);
                context.put("package",env.getProperty("addon.package","com.sap"));

                // System.out.println(writer.toString());
                //Save the item.xml in a common location
                //String pathOfItemsXml =  env.getProperty("workspace.path","com.sap") + env.getProperty("addon.path","com.sap") + env.getProperty("addon.name","com.sap") + "/resources";

                String filename = env.getProperty("addon.prefix","crd") + itemsModel.getCode().toLowerCase()  + env.getProperty("addon.suffix","crd") + "-items.xml";


                //create and publish event
                AfterItemsXMLCreationEvent afterItemsXMLCreationEvent = new AfterItemsXMLCreationEvent(this ,  jobid , itemsModel.getCode(),itemsModel.getOperation());
                //uncomment this to call graddle tasks
                applicationEventPublisher.publishEvent(afterItemsXMLCreationEvent);
                return new ResponseEntity("Product is updated successfully", HttpStatus.OK);
            } catch (JSONException e) {
                log.error(e.getMessage());
                throw new Exception("Error with the request data- payload is"+payload, e );
            }
            return new ResponseEntity("CRD db has been updated with models and enum from developer workspace and hybris db", HttpStatus.OK);

        }
        catch(Exception ex)
        {
            log.error("not able to update item metadata table due to exception" + ex.getMessage() + ex.getCause() );
            return new ResponseEntity("not able to update item metadata table due to exception --" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        catch(ItemException Itemex)
        {
            log.error("not able to update item metadata table due to exception" + Itemex.getMessage() + Itemex.getCause() );
            return new ResponseEntity("not able to update item metadata table due to exception --" + Itemex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public void classDecompiler(Path file) throws IOException {
        // reader is file which we need to put in database.
        BufferedReader reader = Files.newBufferedReader(file);
        String packageName = null;
        String itemModelName = null;
        String line = null;
        int foundData = 0, posEnd;
        while ((line = reader.readLine()) != null && foundData < 2) {
            int pos = line.indexOf("package");
            if (pos != -1) {
                packageName = line.substring(pos + 8, line.indexOf(";", pos + 8));
                foundData += 1;
            }
            pos = line.indexOf("public class");
            if (pos != -1) {
                posEnd = line.indexOf("Model", pos + 13);
                itemModelName = line.substring(pos + 13, (posEnd == -1) ? line.length() : posEnd );
                foundData += 1;
            }

        }
        log.info(itemModelName + " " + packageName);


        if (itemModelName == null || itemModelName.isEmpty())
        {
            log.error("could not save file with model " + itemModelName + " or package"     + packageName);
            return;
        }
        ItemsMetadataModel obj = new ItemsMetadataModel(itemModelName, packageName);
        saveObject(obj);
    }

    public ResponseEntity streamItemsMetaDataModel() {


         PreparedStatement statement;
         Connection con;

        try {
            //  Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection(dbURl , user, password);
            List<ItemsMetadataModel> items = itemsMetaDataRepository.findAll();
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
        return new ResponseEntity("attributes have been updated from developer's hybris db", HttpStatus.OK);


    }

    public ResponseEntity updateItemsMetaDataModel(JSONObject obj) throws JSONException {

        String itemmodelname = (String) obj.get("itemmodelname");
        String msg = "ItemMetaDataModel has been updated";
        ItemsMetadataModel model = getItemsMetadataModelbyName(itemmodelname);
        if (model == null) {
            log.info(" model with name " +itemmodelname + " not found in the itemmetadata update call" );
            model = new ItemsMetadataModel();
            model.setItemmodelname(itemmodelname);
            msg = "new ItemMetaDataModel has been created";
        }
        for (int i = 0; i < obj.names().length(); i++) {

            switch (obj.names().getString(i)) {
                case "modelpackage": {
                    model.setModelpackage(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "dtopackage": {
                    model.setDtopackage(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "converter": {
                    model.setConverter(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "formname": {
                    model.setFormname(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "extensionname": {
                    model.setExtensionname(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "servicename": {
                    model.setServicename(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "servicepackage": {
                    model.setServicepackage(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "enum": {
                    model.setEnum(Boolean.parseBoolean(obj.get(obj.names().getString(i)).toString()));
                    break;
                }
                case "dtoname": {
                    model.setDtoname(obj.get(obj.names().getString(i)).toString());
                    break;
                }
                case "reverseconverter": {
                    model.setReverseconverter(obj.get(obj.names().getString(i)).toString());
                    break;
                }


            }
        }

        saveModel(model);
        log.info(" model with name " +itemmodelname + " has been updated in the itemmetadata update call" );

        return new ResponseEntity(msg, HttpStatus.OK);

    }


//            } catch (JSONException e) {
//                log.error("populateRelationModel -Error fetching attribute" , e.getMessage());
//            }
//
    public ResponseEntity deleteByname(String modelName) {

        try {
            itemsMetaDataRepository.deleteById(modelName);
            return new ResponseEntity("model has been deleted", HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.error("could not delete itemmodel" + modelName + "model not present in database");
            return new ResponseEntity("model could not deleted --" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    public void pupoluateEnum() throws SQLException, ClassNotFoundException {


        PreparedStatement statement;
        Connection con;

        try {
          //  Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection(dbURl , user, password);

            String[] attribute = new String[2] ;
            String query = "SELECT InternalCode, InternalCodeLowerCase FROM composedtypes where jaloClassName = ? ";
            statement = con.prepareStatement(query);
            statement.setString(1, FILTER_TO_SELECT_ENUM);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                attribute [0] = rs.getString(1);
                attribute [1] = rs.getString(2);

                ItemsMetadataModel obj = new ItemsMetadataModel(attribute [0], "", true);
                saveObject(obj);

            }
            con.close();

        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }


    }
};
