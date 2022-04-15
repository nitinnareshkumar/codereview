package com.review.code.RapidDev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "itemsmetadata")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class ItemsMetadataModel {


    private String itemmodelname;

    @Getter @Setter
    private String modelpackage;
    @Getter @Setter
    private String dtoname;
    @Getter @Setter
    private String dtopackage;
    @Getter @Setter
    private String converter;
    @Getter @Setter
    private String reverseconverter;
    @Getter @Setter
    private String formname;
    @Getter @Setter
    private String extensionname;
    @Getter @Setter
    private String servicename;
    @Getter @Setter
    private String servicepackage;
    @Getter @Setter
    private boolean isEnum;

    public ItemsMetadataModel() {
    }

    public ItemsMetadataModel(String itemModelName, String modelPackage) {
        this.itemmodelname = itemModelName;
        this.modelpackage = modelPackage;
        this.dtopackage = "";
        this.converter = "";
        this.extensionname = "";
        this.formname = "";
    }

    public ItemsMetadataModel(String itemModelName, String modelPackage , Boolean isEnum) {
        this.itemmodelname = itemModelName;
        this.modelpackage = modelPackage;
        this.dtopackage = "";
        this.converter = "";
        this.extensionname = "";
        this.formname = "";
        this.isEnum = isEnum;
    }
    @Id
    public String getItemmodelname() {
        return itemmodelname;
    }

    public void setItemmodelname(String itemmodelname) {
        this.itemmodelname = itemmodelname;
    }

    public void setModelpackage(String modelpackage) {
        this.modelpackage = modelpackage;
    }

    public void setDtoname(String dtoname) {
        this.dtoname = dtoname;
    }

    public void setDtopackage(String dtopackage) {
        this.dtopackage = dtopackage;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public void setReverseconverter(String reverseconverter) {
        this.reverseconverter = reverseconverter;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public void setExtensionname(String extensionname) {
        this.extensionname = extensionname;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public void setServicepackage(String servicepackage) {
        this.servicepackage = servicepackage;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }
}
