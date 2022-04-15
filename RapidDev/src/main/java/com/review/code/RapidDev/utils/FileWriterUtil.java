package com.review.code.RapidDev.utils;

import com.review.code.RapidDev.constants.RapidDevConstants;
import org.apache.velocity.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.*;

@Service
public class FileWriterUtil {

    Logger logger = LoggerFactory.getLogger(FileWriterUtil.class);

    @Autowired
    private Environment env;

    @Autowired
    private FileMapUtil fileMapUtil;


    public Boolean createFileAndSaveToTempAddOn(StringWriter writer, String filename) {
        try {

            final String pathOfItemsXmlinTempAddon = env.getProperty("pathOfItemsXmlinTempAddon.path");

            checkAndCreateAddonTempFolder(pathOfItemsXmlinTempAddon);

            //String pathOfItemsXmlinTempAddon = "classes/addOnTemp/"; //for jar
            FileWriter fw = new FileWriter(pathOfItemsXmlinTempAddon + filename, false);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(writer.toString());
            //Closing BufferedWriter Stream
            bw.close();
            logger.info(filename + " successfully copied to  " + "src/main/resources/addOnTemp/");
            // System.out.println(filename + " successfully copied to  " + "src/main/resources/addOnTemp/" );

            /*Path temp = Files.move
                    (Paths.get(pathOfItemsXml + filename),
                            Paths.get(pathOfItemsXml + "/" + filename), REPLACE_EXISTING);

            if(temp != null)
            {
                System.out.println("File renamed and moved successfully");
            }
            else
            {
                System.out.println("Failed to move the file");
            }*/
            return true;

        } catch (IOException ioe) {
            logger.error("Exception occurred:");
            //System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;
        }

    }

    private void checkAndCreateAddonTempFolder(final String pathOfItemsXmlinTempAddon)
    {
        final File addonTempFolder = new File(pathOfItemsXmlinTempAddon);
        if(!addonTempFolder.exists())
        {
            addonTempFolder.mkdirs();
        }
    }


    //TODO add code to check whether the addon is created, dont move the files if adddon is not crearted
    public Boolean moveToAddOnOnDisk(String typeName, String operation) {
        Map<String, String> listOfFiles = fileMapUtil.getFileMapForMovingToAddon(typeName,operation);
        String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
        String addOnSuffix = env.getProperty("addon.suffix", "addon");
        String filePrefixLowercase = (addOnPrefix + typeName + addOnSuffix).toLowerCase();
        String tempDestinationPathofFile = "";
        String addOnPackagePath = env.getProperty("addon.package", "com.sap.crd").replace('.', '/');
        String pathOfItemsXmlinTempAddon = env.getProperty("pathOfItemsXmlinTempAddon.path");
        //String pathOfItemsXmlinTempAddon = "classes/addOnTemp/"; //for jar
        /*listOfFiles.put(filePrefixLowercase + "-items.xml", "itemsXml");
        listOfFiles.put(filePrefixLowercase + "-beans.xml", "beansXml");
        listOfFiles.put(filePrefixLowercase + "-spring.xml", "springXml");
        listOfFiles.put(filePrefixLowercase + "-web-spring.xml", "webSpringXml");
        listOfFiles.put("base_en.properties", "labelProperties");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "cms-content.impex", "contentImpexes");


        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Service.java", "service");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Service.java", "serviceImpl");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Dao.java", "dao");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Dao.java", "daoImpl");

        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Facade.java", "facade");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Populator.java", "populator");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "ReversePopulator.java", "reversepopulator");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Facade.java", "facadeImpl");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "PageController.java", "controller");
        listOfFiles.put(addOnPrefix + typeName.toLowerCase() + addOnSuffix + "Constants.java", "controllerconstant");
        listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "Page.jsp", "views");
        listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "CreatePage.jsp", "createview");
        listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "UpdatePage.jsp", "updateview");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Form.java", "form");*/
        String addOnPath = env.getProperty("workspace.path", "com.sap") + env.getProperty("addon.path", "com.sap") + addOnPrefix.toLowerCase() + typeName.toLowerCase() + addOnSuffix;
        Path target;

        if (!Files.exists(Paths.get(addOnPath))) {
            logger.error(" unable to move files as addon " + addOnPrefix + typeName + addOnSuffix + "is not created already");
            return false;
        }

        try {
            for (Map.Entry<String, String> entry : listOfFiles.entrySet()) {
                switch (entry.getValue()) {
                    case "beansXml":
                    case "springXml":
                    case "itemsXml": {
                        tempDestinationPathofFile = addOnPath + "/resources/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "webSpringXml": {
                        tempDestinationPathofFile = addOnPath + "/resources/" + addOnPrefix.toLowerCase() + typeName.toLowerCase() + addOnSuffix + "/web/spring/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/" + addOnPrefix.toLowerCase()  + typeName.toLowerCase()  + addOnSuffix + "/web/spring/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "contentImpexes": {

                        tempDestinationPathofFile = addOnPath + "/resources/" + addOnPrefix.toLowerCase()  + typeName.toLowerCase()  + addOnSuffix + "/import/contentCatalogs/apparel-ukContentCatalog/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/" + addOnPrefix.toLowerCase()  + typeName.toLowerCase()  + addOnSuffix + "/import/contentCatalogs/apparel-ukContentCatalog/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "labelProperties": {
                        tempDestinationPathofFile = addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/messages/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/messages/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "service": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/services/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/services/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "serviceImpl": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/services/impl/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/services/impl/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "facade": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/facade/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/facade/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "reversepopulator": {
                    }
                    case "populator": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/facade/populator/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/facade/populator/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "facadeImpl": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/facade/impl/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/facade/impl/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "controller": {
                        tempDestinationPathofFile = addOnPath + "/acceleratoraddon/web/src/" + addOnPackagePath + "/controllers/pages/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/acceleratoraddon/web/src/" + addOnPackagePath + "/controllers/pages/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "controllerconstant": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/constants/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/dao/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "form": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/forms/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/forms/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "createview" : {
                        ;
                    }
                    case "updateview" : {
                        ;
                    }
                    case "views": {
                        tempDestinationPathofFile = addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/views/responsive/pages/"+ typeName.toLowerCase()+"/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/views/responsive/pages/" + typeName.toLowerCase());
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "dao": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/dao/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/dao/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "daoImpl": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/dao/impl/" + entry.getKey();

                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/dao/impl/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                }
            }

        } catch (IOException ioe) {
            logger.error("Exception occurred:");
            // System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;
        }
        logger.info("flies have been successfully moved to addon on disk :");
        //System.out.println("flies have been successfully moved to addon on disk :");
        return true;


    }

    /**
     * @param storeName
     * @param operation
     * @param extType
     * @return
     */
    public Boolean moveFilesToServicesExtension(String storeName, String operation, String extType, String typeCode) {
        Map<String, String> listOfFiles = fileMapUtil.getFileMapForMovingToExtension(storeName,operation,extType, typeCode);
        String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
        String addOnSuffix = env.getProperty("addon.suffix", "addon");
        String filePrefixLowercase = (addOnPrefix + storeName).toLowerCase();
        String tempDestinationPathofFile = "";
        String addOnPackagePath = env.getProperty("addon.package", "com.sap.crd").replace('.', '/');
        String pathOfItemsXmlinTempAddon = env.getProperty("pathOfItemsXmlinTempAddon.path");

        String addOnPath = env.getProperty("workspace.path", "com.sap") + env.getProperty("addon.path", "com.sap") + (addOnPrefix + storeName + extType).toLowerCase();
        String cssPath = env.getProperty("workspace.path", "com.sap") + env.getProperty("addon.path", "com.sap");
        Path target;

        if (!Files.exists(Paths.get(addOnPath))) {
            logger.error(" unable to move files as addon " + addOnPrefix + storeName + extType + "is not created already");
            return false;
        }

        try {
            for (Map.Entry<String, String> entry : listOfFiles.entrySet()) {
                switch (entry.getValue()) {
                    case "extensioninfo":{
                        tempDestinationPathofFile = addOnPath + "/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "beansXml":
                    case "springXml":
                    case "itemsXml": {
                        tempDestinationPathofFile = addOnPath + "/resources/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "contentImpexes": {

                        tempDestinationPathofFile = addOnPath + "/resources/impex/projectdata-" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/impex/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "style": {

                        tempDestinationPathofFile = cssPath + storeName + "/" + storeName + "storefront" + "/web/webroot/_ui/responsive/theme-alpha/css/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(cssPath + storeName + "/" + storeName + "storefront" + "/web/webroot/_ui/responsive/theme-alpha/css/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "productImpexes": {

                        tempDestinationPathofFile = addOnPath + "/resources/impex/projectdata-" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/resources/impex/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "labelProperties": {
                       /* tempDestinationPathofFile = addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/messages/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/messages/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;*/
                    }
                    case "productTabView": {
                        tempDestinationPathofFile = addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/views/responsive/pages/"+ typeCode.toLowerCase()+"/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/acceleratoraddon/web/webroot/WEB-INF/views/responsive/pages/" + typeCode.toLowerCase());
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                    case "populator": {
                        tempDestinationPathofFile = addOnPath + "/src/" + addOnPackagePath + "/product/converters/populators/" + entry.getKey();
                        if (Files.exists(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()))) {
                            target = Paths.get(addOnPath + "/src/" + addOnPackagePath + "/product/converters/populators/");
                            Files.createDirectories(target);
                            Path temp = Files.move(Paths.get(pathOfItemsXmlinTempAddon + entry.getKey()), Paths.get(tempDestinationPathofFile), REPLACE_EXISTING);
                        }
                        break;
                    }
                }
            }

        } catch (IOException ioe) {
            logger.error("Exception occurred:");
            // System.out.println("Exception occurred:");
            ioe.printStackTrace();
            return false;
        }
        logger.info("files have been successfully moved to addon on disk :");
        //System.out.println("flies have been successfully moved to addon on disk :");
        return true;


    }
    /**
     * @param operation
     * @return velocityTemplatePath : The path where the velocity template is located
     */
    public String findVelocityTemplatePathByOperation(String operation) {
        Assert.notNull(operation, "Operation name not supplied . It cannot be null");
        switch (operation.toLowerCase()) {
            case "newitem_listview": {
            }
            case "newitem_crud": {
                String velocityTemplatePath = env.getProperty("velocity.template.newitem.crud", "classes/velocity/crud");
                return velocityTemplatePath;
            }
            case RapidDevConstants.OPERATION_NEWSTORE_SERVICES: {
                String velocityTemplatePath = env.getProperty("velocity.template.newstore.services", "classes/velocity/newStore/services");
                return velocityTemplatePath;
            }
            case RapidDevConstants.OPERATION_NEWSTORE_FACADES: {
                String velocityTemplatePath = env.getProperty("velocity.template.newstore.facades", "classes/velocity/newStore/facades");
                return velocityTemplatePath;
            }
            case RapidDevConstants.OPERATION_NEWSTORE_ADDON: {
                String velocityTemplatePath = env.getProperty("velocity.template.newstore.addon", "classes/velocity/newStore/addon");
                return velocityTemplatePath;
            }
            default: {
                String velocityTemplatePath = env.getProperty("velocity.template.default", "classes/velocity/");
                return velocityTemplatePath;
            }
        }
    }

    /**
     * @param storeName
     * @param operation
     * @param extType
     * @param typeCode
     * @return
     */
    public Boolean moveMediaFilesToExtension(String storeName, String operation, String extType, String typeCode) {
        //Map<String, String> listOfFiles = fileMapUtil.getFileMapForMovingToExtension(storeName,operation,extType, typeCode);
        String mediaSourceFolder = env.getProperty("velocity.template.newstore.addon.media");
        String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
        String mediaTargetFolder = env.getProperty("workspace.path", "com.sap") + env.getProperty("addon.path", "com.sap") + (addOnPrefix + storeName + extType).toLowerCase()
                + "/resources/" + (addOnPrefix + storeName + extType).toLowerCase() + "/import/sampledata/images/";
        if(extType.equalsIgnoreCase(RapidDevConstants.SERVICES_EXT)){
            mediaSourceFolder.concat("/productimages");
            mediaTargetFolder.concat("/productimages");
        }
        File sourceDir=new File(mediaSourceFolder);
        File targetDir=new File(mediaTargetFolder);
        if (!Files.exists(Paths.get(mediaSourceFolder))) {
            logger.error(" unable to move files as addon " + addOnPrefix + storeName + extType + "is not created already");
            return false;
        }
        try {
            FileUtils.deleteDirectory(targetDir);
            FileUtils.copyDirectory(sourceDir, targetDir);
            //Files.createDirectories(Paths.get(mediaTargetFolder));
           // Path temp = Files.copy(Paths.get(mediaSourceFolder), Paths.get(mediaTargetFolder), REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Exception occurred:");
            // System.out.println("Exception occurred:");
            e.printStackTrace();
            return false;
        }
        logger.info("Media images have been successfully moved to extension :");
        //System.out.println("flies have been successfully moved to addon on disk :");
        return true;
    }

}
