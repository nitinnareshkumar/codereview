package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Relations {

    private String code;
    private String relationtype;
    private String typecode;
    private String localized;
    private String generate;
    private String autocreate;
    private RelationElement source;
    private RelationElement target;

    // Contains the source and target element attributes

}
