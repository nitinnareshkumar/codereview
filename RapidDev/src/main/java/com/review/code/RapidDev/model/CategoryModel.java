
package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This entity is about self-join. There is an entity that needs self-join.
 * For example, this TreeItem has childs tree items and child tree items have a parent tree item.
 * Self-join is similiar with general join which use @ManyToOne and @OneToMany annotation.
 * The owner of the join has @JoinColumn annotation and non-owner has 'mappedBy' attribute.
 * However, this self-join class all of them in the same class, becuase it is self-join.
 *
 * @author kevin
 *
 */
@Entity(name="Category")
public class CategoryModel {
    @Id
    @GeneratedValue
    private long id;

    @Getter @Setter
    private Long jobid;

    @Getter @Setter
    private Integer code;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Integer categorylevel;

    @Getter @Setter
    @Column(columnDefinition = "boolean default false")
    private Boolean isClassification;


    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent_id")
    private CategoryModel parent;

    @OneToMany(mappedBy="parent", cascade={CascadeType.ALL})
    private Set<CategoryModel> childrens = new HashSet<CategoryModel>();

    public CategoryModel() {
    }
    public long getId() {
        return id;
    }
    public void setId(long itemId) {
        this.id = itemId;
    }

    public long getJobid() {
        return jobid;
    }
    public void setJobid(long jobid) {
        this.jobid = jobid;
    }

    public String getName() {
        return name;
    }
    public void setName(String title) {
        this.name = title;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public CategoryModel getParent() {
        return parent;
    }
    public void setParent(CategoryModel parent) {
        this.parent = parent;
    }
    public Set<CategoryModel> getChildrens() {
        return childrens;
    }
    public void setChildrens(Set<CategoryModel> childs) {
        this.childrens = childs;
    }
    public void addChild(CategoryModel child) {
        this.childrens.add(child);
    }



    public String toString()  {
        return String.format("itemId : %d, title : %s, parent id : %d%n", id, name, parent == null ? -1 : parent.getId());
    }
}