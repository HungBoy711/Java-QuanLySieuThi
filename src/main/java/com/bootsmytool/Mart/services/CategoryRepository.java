package com.bootsmytool.Mart.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bootsmytool.Mart.models.Category;
import com.bootsmytool.Mart.models.Product;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	Page<Category> findAll(Pageable pageable);
}
