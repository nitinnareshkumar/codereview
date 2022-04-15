package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "itemtype")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class ItemsModel {

    private Long id;
    //JOBID should be same for all new ItemType, Relation, ENum, Colelction HYbris item types
    // that are submitted for creation in one request
    @Getter @Setter
    private Long jobid;

    public String getCode() {
        return code;
    }

    @Getter @Setter
    private String code;
    @Getter @Setter
    private String jaloClass;
    @Getter @Setter
    private String deployment;
    @Getter @Setter
    private String typecode;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String operation;
    @Getter @Setter
    private boolean isEnum;

    //@ElementCollection
    private Set<AttributesModel> attributes;

    @OneToMany(mappedBy = "itemtype" , cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public Set<AttributesModel> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributesModel> attributes) {
        this.attributes = attributes;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
