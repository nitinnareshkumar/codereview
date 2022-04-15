package com.review.code.RapidDev.utils;

import com.review.code.RapidDev.model.*;
import com.review.code.RapidDev.pojo.item.*;
import com.review.code.RapidDev.repository.AttributeRepository;
import com.review.code.RapidDev.service.*;
import com.sap.crd.RapidDev.model.*;
import com.sap.crd.RapidDev.pojo.item.*;
import com.sap.crd.RapidDev.service.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemsXmlUtil {
    Logger log = LoggerFactory.getLogger(ItemsXmlUtil.class);

    final String coreJalo= ".core.jalo.";

    @Value("${addon.package}")
    private String packageName;
    @Autowired
    RelationService relationService;
    @Autowired
    FeatureService featureService;

    @Autowired
    ItemTypeService itemTypeService;
    @Autowired
    StoreMetadataService storeMetadataService;
    @Autowired
    CategoryMetadataService categoryMetadataService;

    @Autowired
    ColorMetadataService colorMetadataService;

    @Autowired
    AttributeRepository attributeRepository;

    /**
     * Get ItemType Array from TypeGroup
     *
     * @param enumArray the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted item types Array object
     * Status : Potential-Use - Can be repurposed to generate ENUM - Invoked by generateItemXml method of the Controller
     */
    public List<EnumType> getEnumFromJSONObject(JSONArray enumArray) {
        List<EnumType> enumObjList = new ArrayList<>();
        //List<Map> enumValueList = new ArrayList();
        JSONObject enumTemp = new JSONObject();
        Map enumCode = new HashMap();
        for (int i = 0; i < enumArray.length(); i++) {
            try {
                EnumType enumObject = new EnumType();
                List<Map> enumValueList = new ArrayList<>();
                enumTemp = (JSONObject) enumArray.get(i);
                enumObject.setEnumCode(enumTemp.getString("_code"));
                if (!enumTemp.isNull("_autocreate")) {
                    enumObject.setAutocreate(enumTemp.getString("_autocreate"));
                }
                if (!enumTemp.isNull("_generate")) {
                    enumObject.setGenerate(enumTemp.getString("_generate"));
                }
                if (!enumTemp.isNull("_dynamic")) {
                    enumObject.setDynamic(enumTemp.getString("_dynamic"));
                }

                //enumCode.put("code",enumTemp.getString("_code"));
                if (!enumTemp.isNull("value")) {
                    JSONArray valueArray = enumTemp.getJSONArray("value");
                    List<String> values = new ArrayList<>();
                    for (int j = 0; j < valueArray.length(); j++) {
                        JSONObject ja = (JSONObject) valueArray.get(j);
                        values.add(ja.getString("_code"));
                    }
                    enumObject.setValueList(values);
                    enumObjList.add(enumObject);
                }

                //enumTypeList.add(enumValueList);

            } catch (JSONException e) {
                log.error("getEnumFromJSONObject -Error fetching attribute" , e.getMessage());
            }

        }
        return enumObjList;
    }

    /**
     * Populate the Relation POJO from relation Object
     *
     * @param relationArray the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public Relations getRelationFromJSONObject(JSONArray relationArray) {
        Relations relationsType = new Relations();
        relationsType.setSource(new RelationElement());
        relationsType.setTarget(new RelationElement());
        relationsType.setAutocreate("true");
        relationsType.setGenerate("true");
        relationsType.setLocalized("false");
        for (int i = 0; i < relationArray.length(); i++) {
            try {
                JSONObject nameValueObject = relationArray.getJSONObject(i);
                switch (nameValueObject.getString("name")) {
                    case "relationType":{
                        relationsType.setRelationtype(nameValueObject.getString("value"));
                        break;
                    }
                    case "typecode":{
                        relationsType.setTypecode(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceRead": {
                        relationsType.getSource().getModifier().setRead(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceWrite": {
                        relationsType.getSource().getModifier().setWrite(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceRelation": {
                        relationsType.getSource().setType(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceQualifier": {
                        relationsType.getSource().setQualifier(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceSearch": {
                        relationsType.getSource().getModifier().setSearch(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceOptional": {
                        relationsType.getSource().getModifier().setOptional(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceCardinality": {
                        relationsType.getSource().setCardinality(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourcecollectionType": {
                        relationsType.getSource().setCollectiontype(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceOrdered": {
                        relationsType.getSource().setOrdered(nameValueObject.getString("value"));
                        break;
                    }
                    //target attributes
                    case "targetRead": {
                        relationsType.getTarget().getModifier().setRead(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetWrite": {
                        relationsType.getTarget().getModifier().setWrite(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetRelation": {
                        relationsType.getTarget().setType(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetQualifier": {
                        relationsType.getTarget().setQualifier(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetSearch": {
                        relationsType.getTarget().getModifier().setSearch(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetOptional": {
                        relationsType.getTarget().getModifier().setOptional(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetCardinality": {
                        relationsType.getTarget().setCardinality(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetcollectionType": {
                        relationsType.getTarget().setCollectiontype(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetOrdered": {
                        relationsType.getTarget().setOrdered(nameValueObject.getString("value"));
                        break;
                    }
                    //Relation Headers
                    case "code": {
                        relationsType.setCode(nameValueObject.getString("value"));
                        break;
                    }
                    case "localized": {
                        relationsType.setLocalized(nameValueObject.getString("value"));
                        break;
                    }
                    case "generate": {
                        relationsType.setGenerate(nameValueObject.getString("value"));
                        break;
                    }
                    case "autocreate": {
                        relationsType.setAutocreate(nameValueObject.getString("value"));
                        break;
                    }
                }
            } catch (JSONException e) {
                log.error("getRelationFromJSONObject -Error fetching attribute" , e.getMessage());
            }
        }
        //Set the relation code
        relationsType.setCode(relationsType.getSource().getType() + "2" + relationsType.getTarget().getType()+"Rel");
        //save the relation in db
        //relationService.saveRelations(relationsType);
        return relationsType;
    }

    /**
     * Populate the Relation POJO LIST from relation JSONArray from Payload
     * This method calls the @getRelationFromJSONObject to populate individual
     * Relation POJO.
     *
     * @param itemArray the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted item types Array object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public List<ItemType> getItemListFromJSONObject(JSONArray itemArray) {

        List<ItemType> itemList = new ArrayList<>();
        JSONObject tempItem;
        ItemType itemType = new ItemType();
        for (int i = 0; i < itemArray.length(); i++) {
            try {
                tempItem = (JSONObject) itemArray.get(i);
                if(!tempItem.isNull("header")) {
                    itemType = getItemHeaderFromJSONObject(tempItem,itemType);
                }
                else if (!tempItem.isNull("attributes")){
                    itemType = getItemAttributesFromJSONObject(tempItem,itemType);
                }
                //ItemType item = getItemFromJSONObject(tempItem);
            } catch (JSONException e) {
                log.error("getItemListFromJSONObject -Error fetching attribute" , e.getMessage());
            }

        }
        itemList.add(itemType);
        return itemList;
    }

    /**
     * Populate the Relation POJO from relation Object
     *
     * @param headerObject the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked within this class
     */
    protected ItemType getItemHeaderFromJSONObject(JSONObject headerObject,ItemType item) {

        try {
            JSONArray headerList = headerObject.getJSONArray("header");
            JSONObject tempJSONObject;
            for (int i = 0; i < headerList.length(); i++) {
                tempJSONObject = (JSONObject) headerList.get(i);
                switch (tempJSONObject.getString("name")) {
                    case "code": {
                        item.setCode(tempJSONObject.getString("value"));
                        break;
                    }
                    case "jaloClass": {
                        item.setJaloClass(tempJSONObject.getString("value"));
                        break;
                    }
                    case "deployment": {
                        item.setDeployment(tempJSONObject.getString("value"));
                        break;
                    }
                    case "typecode": {
                        item.setTypecode(tempJSONObject.getString("value"));
                        break;
                    }
                    case "description": {
                        item.setDescription(tempJSONObject.getString("value"));
                        break;
                    }
                    case "operation": {
                        item.setOperation(tempJSONObject.getString("value"));
                        break;
                    }
                }
            }
            item.setJaloClass(packageName + coreJalo+ item.getCode());
        } catch (JSONException e) {
            log.error("getItemHeaderFromJSONObject -Error fetching attribute" , e.getMessage());
        }
        return item;
    }

    /**
     * Populate the Relation POJO from relation Object
     *
     * @param attributesObject the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted attributes object
     * Status : IN-USE
     */
    public ItemType getItemAttributesFromJSONObject(JSONObject attributesObject,ItemType item) {
        try {
            JSONArray attributesList = attributesObject.getJSONArray("attributes");
            JSONArray attributeRowList;
            JSONObject tempJSONObject;
            JSONObject tempAttrJSONObject;
            //Parse through the attributes Array and then through another array of attribute which has values in it
            for(int i=0; i< attributesList.length(); i++){
                attributeRowList= ((JSONObject)attributesList.get(i)).getJSONArray("attributeRow");
                ItemAttributes itemAttribute = new ItemAttributes();
                //Parse through the attributeRows
                for(int j=0; j< attributeRowList.length();j++){
                    tempJSONObject = attributeRowList.getJSONObject(j);

                    switch (tempJSONObject.getString("name")){
                        case "qualifier" :
                        {
                            itemAttribute.setQualifier(tempJSONObject.getString("value"));
                            break;

                        }
                        case "dataTypeSelect" :
                        {
                            itemAttribute.setDataTypeSelect(getAtomicTypeClass(tempJSONObject.getString("value")));
                            break;
                        }
                        case "propertyOrJalo" :
                        {
                            itemAttribute.setPropertyOrJalo(tempJSONObject.getString("value"));
                            break;
                        }
                        case "autocreate" :
                        {
                            itemAttribute.setAutocreate(tempJSONObject.getString("value"));
                            break;
                        }
                        case "generate" :
                        {
                            itemAttribute.setGenerate(tempJSONObject.getString("value"));
                            break;
                        }
                        case "read" :
                        {
                            itemAttribute.getAttrModifiers().setRead(tempJSONObject.getString("value"));
                            break;
                        }
                        case "write" :
                        {
                            itemAttribute.getAttrModifiers().setWrite(tempJSONObject.getString("value"));
                            break;
                        }
                        case "search" :
                        {
                            itemAttribute.getAttrModifiers().setSearch(tempJSONObject.getString("value"));
                            break;
                        }
                        case "optional" :
                        {
                            itemAttribute.getAttrModifiers().setOptional(tempJSONObject.getString("value"));
                            break;
                        }
                        case "unique" :
                        {
                            itemAttribute.getAttrModifiers().setUnique(tempJSONObject.getString("value"));
                            break;
                        }
                        case "uiName" :
                        {
                            //itemAttribute.getAttrModifiers().set(tempJSONObject.getString("value"));
                            break;
                        }
                        case "validations" :
                        {
                            //itemAttribute.setQualifier(tempJSONObject.getString("value"));
                            break;
                        }
                        case "solrIndex": {
                            itemAttribute.setMakeSolrIndex(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "indexType": {
                            itemAttribute.setIndexType(tempJSONObject.getString("value"));
                            break;
                        }
                        case "solrQuery": {
                            itemAttribute.setMakeSolrQuery(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "solrFacet": {
                            itemAttribute.setMakeSolrFacet(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "facetType": {
                            itemAttribute.setFacetType(tempJSONObject.getString("value"));
                            break;
                        }
                    }
                }
                item.getAttributes().add(itemAttribute);
            }
        } catch (JSONException e) {
            log.error("getItemAttributesFromJSONObject -Error fetching attribute" , e.getMessage());
        }
        return item;
    }

    /**
     * Populate the Relation POJO LIST from relation JSONArray from Payload
     * This method calls the @getRelationFromJSONObject to populate individual
     * Relation POJO.
     *
     * @param itemArray the parameters provided by user for extracting the underlying itemtypes
     * @return newly Created DB itemModel entity
     * extracted item types Array object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller.
     */
    public ItemsModel getItemModelFromJSONObject(JSONArray itemArray) {
        List<ItemsModel> itemList = new ArrayList<>();
        ItemsModel itemsModel = new ItemsModel();
        JSONObject tempItem;
        //ItemType itemType = new ItemType();
        for (int i = 0; i < itemArray.length(); i++) {
            try {
                tempItem = (JSONObject) itemArray.get(i);
                if(!tempItem.isNull("header")) {
                    itemsModel = populateItemModelHeader(tempItem,itemsModel);
                }
                else if (!tempItem.isNull("attributes")){
                    itemsModel = populateItemModelAttributes(tempItem,itemsModel);
                }
                //ItemType item = getItemFromJSONObject(tempItem);
            } catch (JSONException e) {
                log.error("getItemModelFromJSONObject -Error fetching attribute" , e.getMessage());
            }
        }
        return itemsModel;
    }
    /**
     * Populate the ItemsModel DB entity from relation Object
     *
     * @param headerObject the parameters provided by user for extracting the underlying itemtypes
     * @return ItemType - the DB specific entity, which will be saved in DB
     * extracted relation object
     * Invoked by :
     * Status : IN-USE
     */
    public ItemsModel populateItemModelHeader(JSONObject headerObject,ItemsModel item) {

        try {
            JSONArray headerList = headerObject.getJSONArray("header");
            JSONObject tempJSONObject;
            for (int i = 0; i < headerList.length(); i++) {
                tempJSONObject = (JSONObject) headerList.get(i);
                switch (tempJSONObject.getString("name")) {
                    case "code": {
                        item.setCode(tempJSONObject.getString("value"));
                        break;
                    }
                    case "jaloClass": {
                        item.setJaloClass(tempJSONObject.getString("value"));
                        break;
                    }
                    case "deployment": {
                        item.setDeployment(tempJSONObject.getString("value"));
                        break;
                    }
                    case "typecode": {
                        item.setTypecode(tempJSONObject.getString("value"));
                        break;
                    }
                    case "description": {
                        item.setDescription(tempJSONObject.getString("value"));
                        break;
                    }
                    case "operation": {
                        item.setOperation(tempJSONObject.getString("value"));
                        break;
                    }
                }
            }
            item.setJaloClass(packageName + coreJalo+ item.getCode());
        } catch (JSONException e) {
            log.error("populateItemModelHeader -Error fetching attribute" , e.getMessage());
        }
        return item;
    }

    /**
     * Populate the ItemType attributes details from user JSON input Object
     *
     * @param attributesObject the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted attributes object
     * Invoked by :
     * Status : IN-USE
     */
    public ItemsModel populateItemModelAttributes(JSONObject attributesObject, ItemsModel item) {
        try {
            JSONArray attributesList = attributesObject.getJSONArray("attributes");
            JSONArray attributeRowList;
            JSONObject tempJSONObject;
            JSONObject tempAttrJSONObject;
            Set<AttributesModel> attributesModelSet = new HashSet<>();
            //Parse through the attributes Array and then through another array of attribute which has values in it
            for(int i=0; i< attributesList.length(); i++){
                attributeRowList= ((JSONObject)attributesList.get(i)).getJSONArray("attributeRow");
                //ItemAttributes itemAttribute = new ItemAttributes();
                AttributesModel itemAttribute = new AttributesModel();
                //Parse through the attributeRows
                for(int j=0; j< attributeRowList.length();j++){
                    tempJSONObject = attributeRowList.getJSONObject(j);

                    switch (tempJSONObject.getString("name")){
                        case "qualifier" :
                        {
                            itemAttribute.setQualifier(tempJSONObject.getString("value"));
                            break;

                        }
                        case "dataTypeSelect" :
                        {
                            itemAttribute.setDatatypeselect(getAtomicTypeClass(tempJSONObject.getString("value")));
                            break;
                        }
                        case "propertyOrJalo" :
                        {
                            itemAttribute.setPropertyorjalo(tempJSONObject.getString("value"));
                            break;
                        }
                        case "autocreate" :
                        {
                            itemAttribute.setAutocreate(tempJSONObject.getString("value"));
                            break;
                        }
                        case "generate" :
                        {
                            itemAttribute.setGenerate(tempJSONObject.getString("value"));
                            break;
                        }
                        case "read" :
                        {
                            itemAttribute.getModifier().setCanread(tempJSONObject.getString("value"));
                            break;
                        }
                        case "write" :
                        {
                            itemAttribute.getModifier().setCanwrite(tempJSONObject.getString("value"));
                            break;
                        }
                        case "search" :
                        {
                            itemAttribute.getModifier().setCansearch(tempJSONObject.getString("value"));
                            break;
                        }
                        case "optional" :
                        {
                            itemAttribute.getModifier().setOptional(tempJSONObject.getString("value"));
                            break;
                        }
                        case "unique" :
                        {
                            itemAttribute.getModifier().setUniqueattr(tempJSONObject.getString("value"));
                            break;
                        }
                        case "uiName" :
                        {
                            itemAttribute.setUiname(tempJSONObject.getString("value"));
                            break;
                        }
                        case "validations" :
                        {
                            //itemAttribute.setQualifier(tempJSONObject.getString("value"));
                            break;
                        }
                        case "uniqueIdForSearch" :
                        {
                            itemAttribute.setUniqueIdForSearch(tempJSONObject.getString("value"));
                            break;
                        }
                        case "solrIndex": {
                            itemAttribute.setMakeSolrIndex(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "indexType": {
                            itemAttribute.setIndexType(tempJSONObject.getString("value"));
                            break;
                        }
                        case "solrQuery": {
                            itemAttribute.setMakeSolrQuery(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "solrFacet": {
                            itemAttribute.setMakeSolrFacet(tempJSONObject.getBoolean("value"));
                            break;
                        }
                        case "facetType": {
                            itemAttribute.setFacetType(tempJSONObject.getString("value"));
                            break;
                        }
                    }
                    itemAttribute.setItemtype(item);
                    attributesModelSet.add(itemAttribute);
                }
                //item.getAttributes().add(itemAttribute);
                item.setAttributes(attributesModelSet);
            }
        } catch (JSONException e) {
            log.error("populateItemModelAttributes -Error fetching attribute" , e.getMessage());
        }
        return item;
    }

    public String getAtomicTypeClass(String typeValue){
        String typeFQClassName = "";
        switch (typeValue.toLowerCase())
        {
            case "string" :
            {
                typeFQClassName = "java.lang.String";
                break;
            }
            case "integer" :
            case "int" :
            {
                typeFQClassName = "java.lang.Integer";
                break;
            }
            case "datetime":
            case "date" :
            {
                typeFQClassName = "java.util.Date";
                break;
            }
            case "float" :
            {
                typeFQClassName = "java.lang.Float";
                break;
            }
            case "double" :
            {
                typeFQClassName = "java.lang.Double";
                break;
            }
            case "long" :
            {
                typeFQClassName = "java.lang.Long";
                break;
            }
            case "short" :
            {
                typeFQClassName = "java.lang.Short";
                break;
            }
            case "boolean" :
            {
                typeFQClassName = "boolean";
                break;
            }
            case "byte" :
            {
                typeFQClassName = "java.lang.Byte";
                break;
            }
            case "char" :
            {
                typeFQClassName = "java.lang.Character";
                break;
            }
            default:
            {
                typeFQClassName = typeValue;
                break;
            }
        }
        return typeFQClassName;
    }

    /**
     * Populate the Relation POJO from relation Object
     *
     * @param relationArray the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public RelationModel populateRelationModel(JSONArray relationArray) {
        RelationModel relationModel = new RelationModel();
        for (int i = 0; i < relationArray.length(); i++) {
            try {
                JSONObject nameValueObject = relationArray.getJSONObject(i);
                switch (nameValueObject.getString("name")) {
                    case "typecode":{
                        relationModel.setTypecode (nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceRead": {
                        relationModel.setSourceread(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceWrite": {
                        relationModel.setSourcewrite(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceRelation": {
                        relationModel.setSourcetype(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceQualifier": {
                        relationModel.setSourcequalifier(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceSearch": {
                        relationModel.setSourcesearch(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceOptional": {
                        relationModel.setSourceoptional(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceCardinality": {
                        relationModel.setSourcecardinality(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourcecollectionType": {
                        relationModel.setSourcecollectiontype(nameValueObject.getString("value"));
                        break;
                    }
                    case "sourceOrdered": {
                        relationModel.setSourceordered(nameValueObject.getString("value"));
                        break;
                    }
                    //target attributes
                    case "targetRead": {
                        relationModel.setTargetread(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetWrite": {
                        relationModel.setTargetwrite(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetRelation": {
                        relationModel.setTargettype(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetQualifier": {
                        relationModel.setTargetqualifier(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetSearch": {
                        relationModel.setTargetsearch(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetOptional": {
                        relationModel.setTargetoptional(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetCardinality": {
                        relationModel.setTargetcardinality(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetcollectionType": {
                        relationModel.setTargetcollectiontype(nameValueObject.getString("value"));
                        break;
                    }
                    case "targetOrdered": {
                        relationModel.setTargetordered(nameValueObject.getString("value"));
                        break;
                    }
                    //Relation Headers
                    case "code": {
                        relationModel.setCode(nameValueObject.getString("value"));
                        break;
                    }
                    case "localized": {
                        relationModel.setLocalized(nameValueObject.getString("value"));
                        break;
                    }
                    case "generate": {
                        relationModel.setGenerate(nameValueObject.getString("value"));
                        break;
                    }
                    case "autocreate": {
                        relationModel.setAutocreate(nameValueObject.getString("value"));
                        break;
                    }
                    case "primaryRelationForSearch": {
                        relationModel.setPrimaryRelationForSearch(nameValueObject.getString("value"));
                        break;
                    }
                }
            } catch (JSONException e) {
                log.error("populateRelationModel -Error fetching attribute" , e.getMessage());
            }
        }
        //save the relation in db
        //relationService.saveRelations(relationModel);
        return relationModel;
    }


    /**
     * Populate the Feature POJO from relation Object
     *
     * @param featureArray the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public FeatureModel populateFeatureModel(JSONArray featureArray, Long jobid) {
        FeatureModel featureModel = new FeatureModel();
        for (int i = 0; i < featureArray.length(); i++) {
            try {
                JSONObject nameValueObject = featureArray.getJSONObject(i);
                switch (nameValueObject.getString("name")) {
                    case "featureName":{
                        featureModel.setName (nameValueObject.getString("value"));
                        break;
                    }
                    case "featureDisplayName":{
                        featureModel.setFeatureDisplayName (nameValueObject.getString("value"));
                        break;
                    }
                    case "featureCode": {
                        featureModel.setCode(nameValueObject.getString("value"));
                        break;
                    }
                    case "featurePosition": {
                        featureModel.setPosition(nameValueObject.getString("value"));
                        break;
                    }
                    case "featureUnit": {
                        featureModel.setUnit(nameValueObject.getString("value"));
                        break;
                    }
                    case "featureType": {
                        featureModel.setFeaturetype(nameValueObject.getString("value"));
                        break;
                    }
                    case "featureClassification": {
                        //need to add job id in the search query to fetch classification code directly
                        CategoryModel categoryModel =  categoryMetadataService.getCategoryMetadataByNameJobId(nameValueObject.getString("value"), jobid);
//                        featureModel.setClassificationcode(String.valueOf(categoryModel.getCode()));

                          featureModel.setClassificationcode(String.valueOf(categoryModel.getCode()));

                        break;
                    }
                    case "solrIndex": {
                        featureModel.setMakeSolrIndex(nameValueObject.getBoolean("value"));
                        break;
                    }
                    case "indexType": {
                        featureModel.setIndexType(nameValueObject.getString("value"));
                        break;
                    }
                    case "solrQuery": {
                        featureModel.setMakeSolrQuery(nameValueObject.getBoolean("value"));
                        break;
                    }
                    case "solrFacet": {
                        featureModel.setMakeSolrFacet(nameValueObject.getBoolean("value"));
                        break;
                    }
                    case "facetType": {
                        featureModel.setFacetType(nameValueObject.getString("value"));
                        break;
                    }

                }
            } catch (JSONException e) {
                log.error("populateFeatureModel -Error fetching attribute" , e.getMessage());
            }
        }

        return featureModel;
    }

    /**
     * Populate the StoreMetadata POJO from JSON payload
     *
     * @param storeMetadata the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public StoreMetadataModel populateStoreMetadataModel(JSONObject storeMetadata) {
        StoreMetadataModel storeMetadataModel = new StoreMetadataModel();
        try {
            JSONArray metadata = storeMetadata.getJSONArray("storeMetadata");
            JSONObject tempJSONObject;
            for (int i = 0; i < metadata.length(); i++) {
                tempJSONObject = (JSONObject) metadata.get(i);
                switch (tempJSONObject.getString("name")) {
                    case "storeName": {
                        storeMetadataModel.setStorename(tempJSONObject.getString("value"));
                        break;
                    }
                    case "contentCatalogName": {
                        storeMetadataModel.setContentcatalogname(tempJSONObject.getString("value"));
                        break;
                    }
                    case "productCatalogName": {
                        storeMetadataModel.setProductcatalogname(tempJSONObject.getString("value"));
                        break;
                    }
                    case "operation": {
                        storeMetadataModel.setOperation(tempJSONObject.getString("value"));
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            log.error("populateStoreMetadataModel -Error fetching store metadata" , e.getMessage());
        }
        return storeMetadataModel;
    }

    /**
     * Populate the ColorMetadata POJO from JSON payload
     *
     * @param ColorMetadata the parameters provided by user for extracting the underlying itemtypes
     * @return List<JSONObject>
     * extracted relation object
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void populateColorMetadata(JSONObject colorMetadata , Long jobid) {
        ColorMetadataModel colorMetadataModel = new ColorMetadataModel();
        try {
            JSONArray metadata = colorMetadata.getJSONArray("colorMetadata");
            JSONObject tempJSONObject;
            for (int i = 0; i < metadata.length(); i++) {
                tempJSONObject = (JSONObject) metadata.get(i);
                switch (tempJSONObject.getString("name")) {
                    case "footerColor": {
                        colorMetadataModel.setFooterColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "footerCopyrightColor": {
                        colorMetadataModel.setFooterCopyrightColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "footerRightColor": {
                        colorMetadataModel.setFooterRightColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "navlinksColor": {
                        colorMetadataModel.setNavlinksColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "navShopInfoColor": {
                        colorMetadataModel.setNavShopInfoColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "navMiniCartColor": {
                        colorMetadataModel.setNavMiniCartColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "navOrderToolsColor": {
                        colorMetadataModel.setNavOrderToolsColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "btnDefaultColor": {
                        colorMetadataModel.setBtnDefaultColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "btnPrimaryColor": {
                        colorMetadataModel.setBtnPrimaryColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "facetColor": {
                        colorMetadataModel.setFacetColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "productDetailsColor": {
                        colorMetadataModel.setProductDetailsColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "breadcrumColor": {
                        colorMetadataModel.setBreadcrumColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "headerColor": {
                        colorMetadataModel.setHeaderColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "breadcrumSectionColor": {
                        colorMetadataModel.setBreadcrumSectionColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "listHeaderColor": {
                        colorMetadataModel.setListHeaderColor(tempJSONObject.getString("value"));
                        break;
                    }
                    case "bodyFont": {
                        colorMetadataModel.setBodyFont(tempJSONObject.getString("value"));
                        break;
                    }

                }
            }
        } catch (JSONException e) {
            log.error("populateStoreMetadataModel -Error fetching store metadata" , e.getMessage());
        }
        colorMetadataModel.setJobid(jobid);
        colorMetadataService.saveModel(colorMetadataModel);
    }


    /**
     *
     *
     * @param CategoriesMetadata the parameters provided by user for extracting the underlying categories
     * @return List<JSONObject>
     *
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void populateCategoriesResursively(JSONObject CategoriesMetadata , Long jobid , Boolean isClassification) {
        CategoryModel categoryModel = new CategoryModel();
        Integer categoryCreated = 0;
        try {
            JSONArray metadata;
            Integer code = 1000;
            if (isClassification == false){
             metadata = CategoriesMetadata.getJSONArray("categories");}
            else {
                metadata = CategoriesMetadata.getJSONArray("classifications");
                code = 10000;
            }
            JSONObject tempJSONObject;
            for (int i = 0; i < metadata.length(); i++) {
                JSONArray tempArray = (JSONArray) metadata.get(i);
                tempJSONObject = (JSONObject) tempArray.get(i);
                Long parentid = jobid;
                categoryModel.setName(tempJSONObject.getString("text"));
                categoryModel.setId(parentid);
                categoryModel.setJobid(jobid);
                categoryModel.setCode(code);
                categoryModel.setCategorylevel(1);
                categoryModel.setIsClassification(isClassification);
                CategoryModel savedModel = categoryMetadataService.saveModel(categoryModel);
                log.info(tempJSONObject.getString("text"));
                JSONArray tempChilds=  (JSONArray)  tempJSONObject.get("children");
                categoryCreated = categoryCreated +1;
                populateCategoryChilds(tempChilds , savedModel , code ,categoryCreated , jobid, 2, isClassification);//Creating Level 2 categories
//
            }
        } catch (JSONException e) {
            log.error("populatecategories -Error fetching categories metadata" , e.getMessage());
        }
    }

    public Integer populateCategoryChilds(JSONArray childs , CategoryModel parent , Integer code, Integer categoryCreated, Long jobid, Integer categoryLevel, Boolean isClassification ) {

        try {
            JSONObject tempJSONObject;
            for (int i = 0; i < childs.length(); i++) {
                CategoryModel categoryModel = new CategoryModel();
                tempJSONObject = (JSONObject) childs.get(i);

                categoryModel.setName(tempJSONObject.getString("text"));

                //categoryModel.setId(parentid + categoryCreated);
                categoryModel.setCode(code + categoryCreated );
                //CategoryModel parentModel = categoryMetadataService.getCategoryMetadataById(parentid);
                categoryModel.setParent(parent);
                categoryModel.setCategorylevel(categoryLevel);// Set the categoy Level Manually , this will hlp us generate the Velocity context better.
                categoryModel.setJobid(jobid);
                categoryModel.setIsClassification(isClassification);
                CategoryModel savedModel = categoryMetadataService.saveModel(categoryModel);

                JSONArray tempChilds=  (JSONArray)  tempJSONObject.get("children");
                if (tempChilds.length() != 0)
                {
                    categoryCreated = populateCategoryChilds(tempChilds ,savedModel, code ,categoryCreated + 1 , jobid, categoryLevel+ 1, isClassification); //Creating Level 3 categories.
                }
                categoryCreated = categoryCreated +1;

            }
        } catch (JSONException e) {
            log.error("populatecategories -Error fetching categories metadata" , e.getMessage());
        }
        return categoryCreated;

    }

    /**
     * @param itemsModel - Item model generated from the input JSON which is to be saved in the DB
     * @param jobid - This is the currentTimeMillis. to represent a unique jobid.
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void saveItem(ItemsModel itemsModel, Long jobid){
        //set the jobid
        itemsModel.setJobid(jobid);
        itemTypeService.saveModel(itemsModel);
    }

    /**
     * @param relationModel - Relation model generated from the input JSON which is to be saved in the DB
     * @param jobid - This is the currentTimeMillis. to represent a unique jobid.
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void saveRelation(RelationModel relationModel, Long jobid){
        //set the jobid
        relationModel.setJobid(jobid);
        relationService.saveModel(relationModel);
    }

    /**
     * @param featureModel - Feature model generated from the input JSON which is to be saved in the DB
     * @param jobid - This is the currentTimeMillis. to represent a unique jobid.
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void saveFeature(FeatureModel featureModel, Long jobid){
        //set the jobid
        featureModel.setJobid(jobid);
        featureService.saveModel(featureModel);
    }

    /**
     * @param storeMetadataModel - Item model generated from the input JSON which is to be saved in the DB
     * @param jobid - This is the currentTimeMillis. to represent a unique jobid.
     * Status : Active-Use - Invoked by generateItemXml method of the Controller
     */
    public void saveStoreMetadata(StoreMetadataModel storeMetadataModel, Long jobid){
        //set the jobid
        storeMetadataModel.setJobid(jobid);
        storeMetadataService.saveModel(storeMetadataModel);
    }
}


