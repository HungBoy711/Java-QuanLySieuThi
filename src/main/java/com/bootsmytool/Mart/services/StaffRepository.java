package com.bootsmytool.Mart.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bootsmytool.Mart.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
	Page<Staff> findAll(Pageable pageable);
}
