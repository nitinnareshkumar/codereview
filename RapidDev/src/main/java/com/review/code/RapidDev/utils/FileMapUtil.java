package com.review.code.RapidDev.utils;

import com.review.code.RapidDev.constants.RapidDevConstants;
import com.review.code.RapidDev.repository.FileMapMetadataRepository;
import com.review.code.RapidDev.model.FileMapMetadataModel;
import org.apache.velocity.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileMapUtil {

    Logger logger = LoggerFactory.getLogger(FileMapUtil.class);

    @Autowired
    private Environment env;

    @Autowired
    FileMapMetadataRepository fileMapMetadataRepository;
    /**
     * @param operation : Type of operation i.e "newitem_crud" for generating crud files
     *                  or "newitem_listview" for generating the list view files for new item
     * @return: map containing the vm template name as key and the target generated file
     * name extension as the value
     * IMPORTANT: Whenever you are adding a new file in this list then makes sure the same is
     *            also accounted for in the @getFileMapForMovingtoAddon method defined in this file
     * @throws Exception
     */
    public Map<String, String> getFileTemplateMapByOperation( String operation) throws Exception {
        Map<String, String> newStoreFileTemplateMap = new HashMap<>();
        if(operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE_SERVICES) ||
                operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE_FACADES)||
                operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE_ADDON)){

            List<FileMapMetadataModel> fileMapMetadataModelList= fileMapMetadataRepository.findByOperationname(operation);//TODO to find a way to differentiate the service and facade extension
            newStoreFileTemplateMap = fileMapMetadataModelList.stream().collect(
                    Collectors.toMap(FileMapMetadataModel::getTemplatefilename, FileMapMetadataModel::getTargetfilename));
            return newStoreFileTemplateMap;
        }
        Map<String, String> fileTemplateMap = Stream.of(new String[][]{
                {"ItemBean.vm", "-beans.xml"},
                {"ItemSpring.vm", "-spring.xml"},
                {"ItemController.vm", "PageController.java"},
                {"ItemControllerConstants.vm", "$lowercaseConstants.java"},
                {"ItemFacadeClass.vm", "$classFacade.java"},
                {"ItemFacadeInterface.vm", "Facade.java"},
                {"ItemPopulatorClass.vm", "$classPopulator.java"},
                {"ItemListPage.vm", "Page.jsp"},
                {"ItemDaoClass.vm", "$classDao.java"},
                {"ItemDaoInterface.vm", "Dao.java"},
                {"ItemServiceClass.vm", "$classService.java"},
                {"ItemServiceInterface.vm", "Service.java"},
                {"Addon-web-spring.vm", "-web-spring.xml"},
                {"AddonImpex.vm", "cms-content.impex"},
                {"AddonLocaleProperties.vm", "base_en.properties"},
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        if(operation.equalsIgnoreCase("newitem_crud")){
            fileTemplateMap.put("ItemForm.vm", "Form.java");
            fileTemplateMap.put("ItemReversePopulatorClass.vm", "$classReversePopulator.java");
            fileTemplateMap.put("ItemCreatePage.vm", "CreatePage.jsp");
            fileTemplateMap.put("ItemUpdatePage.vm", "UpdatePage.jsp");
        }
        else if(operation.equalsIgnoreCase("newitem_blockview")){

            fileTemplateMap.put("ItemBlockViewPage.vm", "BlockViewPage.jsp");
        }
        else if(operation.equalsIgnoreCase("newitem_listview")){
            //do nothing
        }
        else {
            throw new Exception("Operation name is not supported: "+ operation );
        }
        return fileTemplateMap;
    }

    /**
     * @param typeName : Name of the new item type for which files are being generated
     * @param operation: operation name : newitem_crud or newitem_listview
     * @return
     */
    public Map<String, String> getFileMapForMovingToAddon(String typeName, String operation) {
        logger.info("Operation used is: " + operation );
        Map<String, String> listOfFiles = new HashMap();
        String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
        String addOnSuffix = env.getProperty("addon.suffix", "addon");
        String filePrefixLowercase = (addOnPrefix + typeName + addOnSuffix).toLowerCase();


        //Common files for newitem operations
        //List of files whose name starts with lowercase - all xml files, impex files and properties file
        listOfFiles.put(filePrefixLowercase + "-items.xml", "itemsXml");
        listOfFiles.put(filePrefixLowercase + "-beans.xml", "beansXml");
        listOfFiles.put(filePrefixLowercase + "-spring.xml", "springXml");
        listOfFiles.put(filePrefixLowercase + "-web-spring.xml", "webSpringXml");
        listOfFiles.put("base_en.properties", "labelProperties");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "cms-content.impex", "contentImpexes");

        //Service/dao layer files
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Service.java", "service");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Service.java", "serviceImpl");
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Dao.java", "dao");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Dao.java", "daoImpl");

        //Facade layer files
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Facade.java", "facade");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Facade.java", "facadeImpl");
        listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "Populator.java", "populator");

        //UI layer files
        listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "PageController.java", "controller");
        listOfFiles.put(addOnPrefix + typeName.toLowerCase() + addOnSuffix + "Constants.java", "controllerconstant");
        listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "Page.jsp", "views");

        if(operation.equalsIgnoreCase("newitem_crud")){
            // Files only needed for CRUD
            listOfFiles.put("Default" + addOnPrefix + typeName + addOnSuffix + "ReversePopulator.java", "reversepopulator");
            listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "CreatePage.jsp", "createview");
            listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "UpdatePage.jsp", "updateview");
            listOfFiles.put(addOnPrefix + typeName + addOnSuffix + "Form.java", "form");
        }
        else if(operation.equalsIgnoreCase("newitem_listview")){
            // do nothing
        }
        else if(operation.equalsIgnoreCase("newitem_blockview")){
            // Files only needed for CRUD
            listOfFiles.put(addOnPrefix.toLowerCase() + typeName + addOnSuffix + "blockviewPage.jsp", "views");
        }
        else {
            return null;
        }
        return listOfFiles;
    }

    /**
     * @param extName : Name of the new extension for which files are being generated
     * @param operation: operation name : newitem_crud or newitem_listview or newStore
     * @param extType: extension Type : services facades or addon
     * @param typeCode: Item Type code in this case of newStore it is XXXProduct
     * @return
     */
    public Map<String, String> getFileMapForMovingToExtension(String storeName, String operation, String extType, String typeCode) {
        logger.info("Operation used is: " + operation );
        Map<String, String> listOfFiles = new HashMap();
        String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
        String addOnSuffix = env.getProperty("addon.suffix", "addon");
        String filePrefixLowercase = (addOnPrefix + storeName + extType ).toLowerCase();
        if(operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE) && extType.equalsIgnoreCase(RapidDevConstants.SERVICES_EXT)){
            //List of files whose name starts with lowercase - all xml files, impex files and properties file
            listOfFiles.put(filePrefixLowercase + "-items.xml", "itemsXml");
            listOfFiles.put(filePrefixLowercase + "-spring.xml", "springXml");
            listOfFiles.put("categories.impex", "productImpexes");
            listOfFiles.put("categories_en.impex", "productImpexes");
            listOfFiles.put("categories-classifications.impex", "productImpexes");
            listOfFiles.put("categories-classifications_en.impex", "productImpexes");
            listOfFiles.put("classification-feature.impex", "productImpexes");
            listOfFiles.put("solr.impex", "productImpexes");
            listOfFiles.put("solr_en.impex", "productImpexes");
            listOfFiles.put("sampleproduct.impex", "productImpexes");
        }
        else if(operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE) && extType.equalsIgnoreCase(RapidDevConstants.FACADES_EXT)){
            listOfFiles.put(filePrefixLowercase + "-beans.xml", "beansXml");
            listOfFiles.put(filePrefixLowercase + "-spring.xml", "springXml");
            listOfFiles.put("Default" + StringUtils.capitalizeFirstLetter(addOnPrefix)+ StringUtils.capitalizeFirstLetter(typeCode) + "Populator.java", "populator");
            listOfFiles.put("extensioninfo.xml", "extensioninfo");
        }
        else if(operation.equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE) && extType.equalsIgnoreCase(RapidDevConstants.ADDON_EXT)){
            // Files only needed for CRUD
            listOfFiles.put(StringUtils.capitalizeFirstLetter(addOnPrefix) + StringUtils.capitalizeFirstLetter(typeCode) + "DetailsTab.jsp", "productTabView");
            listOfFiles.put("cms-content.impex", "contentImpexes");
            listOfFiles.put("cms-content_en.impex", "contentImpexes");
            listOfFiles.put("style.css", "style");
        }
        else {
            return null;
        }
        return listOfFiles;
    }
}
