package com.review.code.RapidDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.review.code.RapidDev.model.AttributesMetadataModel;

import java.math.BigInteger;
import java.util.List;

public interface AttributesMetadataRepository extends JpaRepository<AttributesMetadataModel, Long> {
    String query = "SELECT item_t0.InternalCode as A, item_t1.QualifierInternal as B, " +
            "item_t2.internalcode as C, " +
            "item_t3.InternalCode as D FROM composedtypes item_t0 JOIN attributedescriptors item_t1 ON " +
            " item_t0.PK =  item_t1.OwnerPkString LEFT JOIN atomictypes AS item_t2 ON " +
            "item_t1.AttributeTypePK  =  item_t2.PK LEFT JOIN composedtypes AS item_t3 ON " +
            "item_t1.AttributeTypePK  =  item_t3.PK WHERE ( item_t0.InternalCode  = :itemModelName) " +
            "and (item_t1.QualifierInternal not in (SELECT  " +
            "item_t1.QualifierInternal FROM composedtypes item_t0 JOIN attributedescriptors item_t1 ON  " +
            "item_t0.PK =  item_t1.OwnerPkString  WHERE  item_t0.InternalCode  = 'Item') )";

    String queryAttribute = "SELECT * from attributesmetadata  WHERE ( itemmodelname  = :itemModelName )";

    @Query(value = query, nativeQuery = true)
    List<Object[]> getAttributeQuery(@Param("itemModelName") String itemModelName);

    @Query(value = queryAttribute, nativeQuery = true)
    List<AttributesMetadataModel> getAttributeByModel(@Param("itemModelName") String itemModelName);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM attributesmetadata where itemmodelname= :a AND" +
            " attributename=:b and attributetype=:c)", nativeQuery = true)
    BigInteger checkIfExists(@Param("a") String itemModelName, @Param("b") String attributename, @Param("c") String attributetype);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM attributesmetadata where itemmodelname= :a AND" +
            " attributename=:b and attributetype is null)", nativeQuery = true)
    BigInteger checkIfExists(@Param("a") String itemModelName, @Param("b") String attributename);
}