package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "attributesmetadata")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
//test changes
public class AttributesMetadataModel {

    @Getter
    @Setter
    private String itemmodelname;
    @Getter
    @Setter
    private String attributename;
    @Getter
    @Setter
    private String attributetype = null;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public AttributesMetadataModel() {
        ;
    }

    public AttributesMetadataModel(String[] attribute) {
        this.itemmodelname = attribute[0].toString();
        this.attributename = attribute[1].toString();
        if (attribute[2] != null)
            this.attributetype = attribute[2].toString();
        if (attribute[3] != null)
            this.attributetype = attribute[3].toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}