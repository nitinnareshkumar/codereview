package com.review.code.RapidDev.pojo.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter @Setter
public class EnumType {

    private String enumCode;
    @NonNull private String autocreate;
    @NonNull private String generate;
    @NonNull private String dynamic;
    @NonNull private List<String> valueList;
}
