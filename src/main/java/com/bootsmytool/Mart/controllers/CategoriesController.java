package com.bootsmytool.Mart.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bootsmytool.Mart.models.Category;
import com.bootsmytool.Mart.models.CategoryDto;
import com.bootsmytool.Mart.models.Product;
import com.bootsmytool.Mart.models.ProductCategory;
import com.bootsmytool.Mart.models.ProductDto;
import com.bootsmytool.Mart.models.Staff;
import com.bootsmytool.Mart.models.StaffDto;
import com.bootsmytool.Mart.services.CategoryRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

	@Autowired
	private CategoryRepository repo;
	
	@GetMapping("")
	public String showCategoryList(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size, 
            Model model) {
		 Page<Category> categoryPage = repo.findAll(PageRequest.of(page, size, Sort.by("categoryid").descending()));
         model.addAttribute("categoryPage", categoryPage);
        return "categories/index";
    }
	
	@GetMapping({"/create"})
	public String showCreateCategoryPage(Model model) {
		CategoryDto categoryDto = new CategoryDto();
	    model.addAttribute("categoryDto", categoryDto);
	    return "categories/CreateCategory";
	}
	
	@PostMapping({"/create"})
	public String CreateCategory(
			@Valid @ModelAttribute CategoryDto categoryDto,
			BindingResult result,
			Model model
			) {
		if (result.hasErrors()) {
		      return "categories/CreateProduct";
		}
		
		Category category = new Category();
		category.setCategoryname(categoryDto.getCategoryname());
		category.setDescription(categoryDto.getDescription());
		
		repo.save(category);
		
	    return "redirect:/categories";
	}
	
	@GetMapping({"/edit"})
	public String showEditCategoryPage(Model model, @RequestParam int categoryid) {
	    try {
	    	Category category= repo.findById(categoryid).get();
	    	model.addAttribute("category",category);
	    	
	    	CategoryDto categoryDto = new CategoryDto();
	    	categoryDto.setCategoryname(category.getCategoryname());
			categoryDto.setDescription(category.getDescription());
			
			model.addAttribute("categoryDto",categoryDto);
	    }
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "categories/EditCategory";
	}
	
	@PostMapping({"/edit"})
	public String EditStaff(Model model, @RequestParam int categoryid,
			@Valid @ModelAttribute CategoryDto categoryDto,
			BindingResult result) {
	    try {
	    	Category category = repo.findById(categoryid).get();
	    	model.addAttribute("category",category);
			if (result.hasErrors()){
				return "staffs/EditStaff";
			}	
		
			category.setCategoryname(categoryDto.getCategoryname());
			category.setDescription(categoryDto.getDescription());
			
			repo.save(category);
		}
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "redirect:/categories";
	}
	
	@GetMapping({"/delete"})
	public String DeleteCategory( @RequestParam int categoryid) {
	    try {
	    	Category category = repo.findById(categoryid).get();
			repo.delete(category);
	    }
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "redirect:/categories";
	}
}
