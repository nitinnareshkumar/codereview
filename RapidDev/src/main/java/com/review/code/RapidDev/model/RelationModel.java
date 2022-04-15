package com.review.code.RapidDev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "relation")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class RelationModel {
    private Long id;
    //JOBID should be same for all new ItemType, Relation, ENum, Colelction HYbris item types
    // that are submitted for creation in one request
    @Getter @Setter
    private Long jobid;
    @Getter @Setter
    private String sourceread;
    @Getter @Setter
    private String typecode;
    @Getter @Setter
    private String sourcewrite;
    @Getter @Setter
    private String sourcesearch;
    @Getter @Setter
    private String sourceoptional;
    @Getter @Setter
    private String sourcetype;
    @Getter @Setter
    private String sourcequalifier;
    @Getter @Setter
    private String sourcecardinality;
    @Getter @Setter
    private String sourcecollectiontype;
    @Getter @Setter
    private String sourceordered;
    @Getter @Setter
    private String targetread;
    @Getter @Setter
    private String targetwrite;
    @Getter @Setter
    private String targetsearch;
    @Getter @Setter
    private String targettype;
    @Getter @Setter
    private String targetoptional;
    @Getter @Setter
    private String targetpartof;
    @Getter @Setter
    private String targetqualifier;
    @Getter @Setter
    private String targetcardinality;
    @Getter @Setter
    private String targetcollectiontype;
    @Getter @Setter
    private String targetordered;
    @Getter @Setter
    private String code;
    @Getter @Setter
    private String localized;
    @Getter @Setter
    @Column(columnDefinition = "varchar(10) default 'true'")
    private String generate;
    @Column(columnDefinition = "varchar(10) default 'true'")
    @Getter @Setter
    private String autocreate;
    @Column(columnDefinition="varchar(10) default 'false'")
    @Getter @Setter
    private String primaryRelationForSearch;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
