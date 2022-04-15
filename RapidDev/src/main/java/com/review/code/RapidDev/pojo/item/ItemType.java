package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class ItemType {
    private String code;
    private String jaloClass;
    private String deployment;
    private String typecode;
    private String description;
    private String operation;
    private List<ItemAttributes> attributes = new ArrayList<>();
}
