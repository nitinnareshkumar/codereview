package com.review.code.RapidDev.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "itemsattribute")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class AttributesModel {

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemtype_id", referencedColumnName = "id")
    public ItemsModel getItemtype() {
        return itemtype;
    }

    public void setItemtype(ItemsModel itemtype) {
        this.itemtype = itemtype;
    }

    private ItemsModel itemtype;
    @Getter @Setter
    private String qualifier;
    @Getter @Setter
    private String datatypeselect;
    @Getter @Setter
    private String propertyorjalo;
    @Getter @Setter
    private String autocreate;
    @Getter @Setter
    private String generate;
    @Getter @Setter
    private String uiname;
    @Getter @Setter
    private String uniqueIdForSearch;
    @Getter @Setter
    @Column(columnDefinition = "boolean default true")
    private Boolean makeSolrIndex;
    @Getter @Setter
    private String indexType;
    @Getter @Setter
    @Column(columnDefinition = "boolean default true")
    private Boolean makeSolrQuery;
    @Getter @Setter
    @Column(columnDefinition = "boolean default true")
    private Boolean makeSolrFacet;
    @Getter @Setter
    private String facetType;
//    private String validation

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_id", referencedColumnName = "id")
    public ModifiersModel getModifier() {
        return modifier;
    }

    public void setModifier(ModifiersModel modifier) {
        this.modifier = modifier;
    }

    private ModifiersModel modifier = new ModifiersModel();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
