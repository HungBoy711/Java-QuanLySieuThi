package com.bootsmytool.Mart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bootsmytool.Mart.models.Staff;
import com.bootsmytool.Mart.models.Supplier;
import com.bootsmytool.Mart.services.StaffRepository;
import com.bootsmytool.Mart.services.SupplierRepository;

@Controller
@RequestMapping("/suppliers")
public class SuppliersController {
	@Autowired
	private SupplierRepository repo;
	
	@GetMapping("")
	public String showSupplierList(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size, 
            Model model) {
        Page<Supplier> supplierPage = repo.findAll(PageRequest.of(page, size, Sort.by("supplierid").descending()));
        model.addAttribute("supplierPage", supplierPage);
        return "suppliers/index";
    }
}
