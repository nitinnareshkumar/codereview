package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Modifier {
    private String optional;
    private String read;
    private String write;
    private String search;
    private String sPrivate;
    private String initial;
    private String removable;
    private String partof;
    private String dontOptimize;
    private String unique;
    private String encrypted;
}