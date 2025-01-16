package com.bootsmytool.Mart.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bootsmytool.Mart.models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	Page<Supplier> findAll(Pageable pageable);
}