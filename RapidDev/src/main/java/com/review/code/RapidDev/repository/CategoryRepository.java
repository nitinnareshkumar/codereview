package com.review.code.RapidDev.repository;

import com.review.code.RapidDev.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
//public interface CategoryRepository extends JpaRepository<TreeItem1, Long> {
//    public ItemsModel findAllByItemId(Long itemId);

    public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
        public Set<CategoryModel> findByJobid(Long Id);
        public Set<CategoryModel> findByJobidAndIsClassification(Long Id,boolean isClassification);
        public CategoryModel findById(long Id);
        //public CategoryModel findByName(String name);

    String queryAttribute = "SELECT * from category  WHERE ( name  = :name ) and ( jobid  = :jobid )";



    @Query(value = queryAttribute, nativeQuery = true)
    CategoryModel findByNameJobid(@Param("name") String name, @Param("jobid") Long jobid);
}
