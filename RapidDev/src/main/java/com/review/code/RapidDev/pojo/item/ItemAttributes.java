package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ItemAttributes {
    private String qualifier;
    private String dataTypeSelect;
    private String propertyOrJalo;
    private String autocreate;
    private String generate;
    private Boolean makeSolrIndex;
    private String indexType;
    private Boolean makeSolrQuery;
    private Boolean makeSolrFacet;
    private String facetType;
    private Modifier attrModifiers = new Modifier();
}
