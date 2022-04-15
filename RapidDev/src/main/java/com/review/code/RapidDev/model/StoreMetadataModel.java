package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "storeMetadata")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class StoreMetadataModel {
    private Long id;
    //JOBID should be same for all new ItemType, Relation, ENum, Colelction HYbris item types
    // that are submitted for creation in one request
    @Getter @Setter
    private Long jobid;
    @Getter @Setter
    private String storename;
    @Getter @Setter
    private String contentcatalogname;
    @Getter @Setter
    private String productcatalogname;
    @Getter @Setter
    private String operation;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
