package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RelationElement{
    private Modifier modifier = new Modifier();
    private String qualifier;
    private String type;
    private String cardinality;
    private String ordered;
    private String collectiontype;
}
