package com.bootsmytool.Mart.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bootsmytool.Mart.models.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
	Page<ProductCategory> findAll(Pageable pageable);
	Page<ProductCategory> findByCategoryname(String categoryname, Pageable pageable);

}

