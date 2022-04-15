package com.review.code.RapidDev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attributemodifier")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class ModifiersModel {

    private Long id;

    @Setter @Getter
    private String optional;
    @Setter @Getter
    private String canread;
    @Setter @Getter
    private String canwrite;
    @Setter @Getter
    private String cansearch;
    @Setter @Getter
    private String sprivate;
    @Setter @Getter
    private String initial;
    @Setter @Getter
    private String removable;
    @Setter @Getter
    private String partof;
    @Setter @Getter
    private String dontoptimize;
    @Setter @Getter
    private String uniqueattr;
    @Setter @Getter
    private String encrypted;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
