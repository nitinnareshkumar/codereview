package com.review.code.RapidDev.listener;

import com.review.code.RapidDev.constants.RapidDevConstants;

import com.review.code.RapidDev.model.StoreMetadataModel;
import com.review.code.RapidDev.service.StoreMetadataService;
import com.review.code.RapidDev.utils.FileWriterUtil;
import org.apache.velocity.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;

import com.review.code.RapidDev.event.AfterItemsXMLCreationEvent;

@Component
public class AfterItemsXMLCreationEventListener implements ApplicationListener<AfterItemsXMLCreationEvent> {

    @Autowired
    private Environment env;
    @Autowired
    GradleTaskService callGradleTasks;
    @Autowired
    ContextGeneratorUtil contextGeneratorUtil;
    @Autowired
    FileWriterUtil fileWriterUtil;
    @Autowired
    StoreMetadataService storeMetadataService;

    private String coreExtTemplate = "yacceleratorcore";
    private String facadeExtTemplate = "yacceleratorfacades";

    Logger log = LoggerFactory.getLogger(AfterItemsXMLCreationEventListener.class);

    /**
     * @param event - AfterItemsXMLCreationEvent has Operation as one of the attribute it contains the original
     *              operation as received from UI .
     */
    @Async
    @Override
    public void onApplicationEvent(AfterItemsXMLCreationEvent event) {

        //Read the properties files for configurable addon params
        String packageName = env.getProperty("addon.package", "com.sap.ibso");
        String installationDir = env.getProperty("workspace.path");
        String storeFront = env.getProperty("addon.storefront");
        if (installationDir == null) {
            log.error("Installation directory is not available!   ABORTING !!!!");
            return;
        }

        log.info("Received spring custom event - " + event.getMessage());
        String tempAddonName = "crd" + event.getTypeCode().toLowerCase() + "addon";

        // Based on operation - decide where addon needs to be generated or an extension of specific type
        if(event.getOperation().equalsIgnoreCase(RapidDevConstants.OPERATION_NEWSTORE)){
            //code to call gradle to create services extension
            StoreMetadataModel storeMetadataModel = storeMetadataService.getStoreMetadataByJobid(event.getJobid());
            //Since the  argument variable names are a bit confusing - adding explanation here
            /**1st Argument storeName == the name of the extension that is to be generated
             * 2nd Arg = addonName == the extensionTemplate name like yacceleratorCore
             * 3rd Arg = ignore
             * 4th Arg = package name
             * 5th Arg = installation Directory
             */
            String addOnPrefix = StringUtils.capitalizeFirstLetter(env.getProperty("addon.prefix", "crd"));
            String extName = addOnPrefix.toLowerCase() + storeMetadataModel.getStorename().toLowerCase()+ RapidDevConstants.SERVICES_EXT;// TODO - get the ext name properly - remove hardcoding
            log.info("calling gradle to create services extension  - " + extName);
            //callGradleTasks.createExtension(extName,coreExtTemplate ,"somid", packageName, installationDir, event.getOperation());
            //Copy the files generated in above step to the newly created services extension
            //fileWriterUtil.moveFilesToServicesExtension(storeMetadataModel.getStorename(), event.getOperation(),RapidDevConstants.SERVICES_EXT, event.getTypeCode() );
            //Move the sample product images
            //fileWriterUtil.moveMediaFilesToExtension(storeMetadataModel.getStorename(), event.getOperation(),RapidDevConstants.SERVICES_EXT , event.getTypeCode());

            extName = addOnPrefix.toLowerCase() + storeMetadataModel.getStorename().toLowerCase()+ RapidDevConstants.FACADES_EXT;
            log.info("calling gradle to create facades extension  - " + extName);
            //Note the second argument is not being used anymore
            callGradleTasks.createExtension(extName,facadeExtTemplate ,"somid", packageName, installationDir, event.getOperation());
            //Copy the files generated in above step to the newly created services extension
            fileWriterUtil.moveFilesToServicesExtension(storeMetadataModel.getStorename(), event.getOperation(),RapidDevConstants.FACADES_EXT , event.getTypeCode());

            extName = addOnPrefix.toLowerCase() + storeMetadataModel.getStorename().toLowerCase()+ RapidDevConstants.ADDON_EXT;
            log.info("calling gradle to create addon extension  - " + extName);
            //Note the second argument is not being used anymore also here the operation name has been hardcoded to newstore_addon to reuse the addon generation from CRUD

            callGradleTasks.createExtension(storeFront, extName, "somid", packageName, installationDir, "newstore_addon");
            //Copy the files generated in above step to the newly created services extension
            fileWriterUtil.moveFilesToServicesExtension(storeMetadataModel.getStorename(), event.getOperation(),RapidDevConstants.ADDON_EXT , event.getTypeCode());
            fileWriterUtil.moveMediaFilesToExtension(storeMetadataModel.getStorename(), event.getOperation(),RapidDevConstants.ADDON_EXT , event.getTypeCode());

            //callGradleTasks.stopUpdateRestart(env.getProperty("workspace.path",installationDir));

        }
        else if(event.getOperation().equalsIgnoreCase("newitem_blockview") ||
                event.getOperation().equalsIgnoreCase("newitem_listview") ||
                event.getOperation().equalsIgnoreCase("newitem_crud")){
            log.info("calling gradle to create extension  - " + tempAddonName);
            //code to call gradle to create and install extension
            callGradleTasks.createExtension(storeFront, tempAddonName, "somid", packageName, installationDir, event.getOperation());

            //Copy the files generated in above step to the newly created addon extension
            fileWriterUtil.moveToAddOnOnDisk(event.getTypeCode(), event.getOperation());

            //callGradleTasks.stopUpdateRestart(env.getProperty("workspace.path",installationDir));
        }
    }
}