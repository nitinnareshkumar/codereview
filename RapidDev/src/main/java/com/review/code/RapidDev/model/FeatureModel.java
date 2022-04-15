package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "features")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class FeatureModel {
    private Long id;
    //JOBID should be same for all new ItemType, Relation, ENum, Colelction HYbris item types
    // that are submitted for creation in one request
    @Getter @Setter
    private Long jobid;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String featureDisplayName;
    @Getter @Setter
    private String code;
    @Getter @Setter
    private String position;
    @Getter @Setter
    private String unit;
    @Getter @Setter
    private String featuretype;
    @Getter @Setter
    private String classificationcode;
    @Getter @Setter
    @Column(columnDefinition = "varchar(10) default 'true'")
    private Boolean makeSolrIndex;
    @Getter @Setter
    private String indexType;
    @Getter @Setter
    @Column(columnDefinition = "varchar(10) default 'true'")
    private Boolean makeSolrQuery;
    @Getter @Setter
    @Column(columnDefinition = "varchar(10) default 'true'")
    private Boolean makeSolrFacet;
    @Getter @Setter
    private String facetType;



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
