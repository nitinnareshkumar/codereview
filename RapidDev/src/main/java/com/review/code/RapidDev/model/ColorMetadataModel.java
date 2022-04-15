package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "colorMetadata")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class ColorMetadataModel {
    private Long id;
    //JOBID should be same for all new ItemType, Relation, ENum, Colelction HYbris item types
    // that are submitted for creation in one request
    @Getter @Setter
    private Long jobid;
    @Getter @Setter
    private String footerColor;
    @Getter @Setter
    private String footerCopyrightColor;
    @Getter @Setter
    private String footerRightColor;
    @Getter @Setter
    private String navlinksColor;
    @Getter @Setter
    private String navShopInfoColor;
    @Getter @Setter
    private String navMiniCartColor;
    @Getter @Setter
    private String navOrderToolsColor;
    @Getter @Setter
    private String btnDefaultColor;
    @Getter @Setter
    private String btnPrimaryColor;
    @Getter @Setter
    private String facetColor;
    @Getter @Setter
    private String productDetailsColor;
    @Getter @Setter
    private String breadcrumColor;
    @Getter @Setter
    private String headerColor;
    @Getter @Setter
    private String breadcrumSectionColor;
    @Getter @Setter
    private String listHeaderColor;
    @Getter @Setter
    private String bodyFont;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
