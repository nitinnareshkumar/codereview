package com.review.code.RapidDev.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "filemapmetadata")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")*/
public class FileMapMetadataModel {

    private Long id;
    @Getter
    @Setter
    private String operationname;
    @Getter
    @Setter
    private String templatefilename;
    @Getter
    @Setter
    private String targetfilename;
    @Getter
    @Setter
    private String extensiontypename;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}