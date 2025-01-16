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

import com.bootsmytool.Mart.models.Product;
import com.bootsmytool.Mart.models.ProductCategory;
import com.bootsmytool.Mart.models.ProductDto;
import com.bootsmytool.Mart.models.Staff;
import com.bootsmytool.Mart.models.StaffDto;
import com.bootsmytool.Mart.services.CategoryRepository;
import com.bootsmytool.Mart.services.ProductCategoryRepository;
import com.bootsmytool.Mart.services.ProductRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private ProductCategoryRepository repoProductCat;
	
	@Autowired
	private CategoryRepository repoCategory;
	
	@GetMapping("")
	public String showProductList(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size, 
            Model model) {
		 Page<ProductCategory> productPage = repoProductCat.findAll(PageRequest.of(page, size, Sort.by("productid").descending()));
         model.addAttribute("productPage", productPage);
        return "products/index";
    }
	
	@GetMapping({"/create"})
	public String showCreateProductPage(Model model) {
		ProductDto productDto = new ProductDto();
	    model.addAttribute("productDto", productDto);
	    model.addAttribute("categories", repoCategory.findAll());
	    return "products/CreateProduct";
	}
	
	@PostMapping({"/create"})
	public String CreateProduct(
			@Valid @ModelAttribute ProductDto productDto,
			BindingResult result,
			Model model
			) {
		if (productDto.getImages().isEmpty()) {
			result.addError(new FieldError("StaffDto", "imagens", "Yeu cau anh"));
		}
		
		if (result.hasErrors()) {
			  model.addAttribute("categories", repoCategory.findAll());
		      return "products/CreateProduct";
		}
		MultipartFile image = productDto.getImages();
		Date createdAt = new Date(0);
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

		try {
		   
		    String uploadDir = "public/images/";
		    Path uploadPath = Paths.get(uploadDir);

		    if (!Files.exists(uploadPath)) {
		        Files.createDirectories(uploadPath);
		    }

		    try (InputStream inputStream = image.getInputStream()) {
		        Files.copy(inputStream, Paths.get(uploadDir + storageFileName), 
		            StandardCopyOption.REPLACE_EXISTING);
		    }
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex.getMessage());
		}
		
		Product product = new Product();
		product.setProductname(productDto.getProductname());
		product.setCategoryid(productDto.getCategoryid());
		product.setImportdate(productDto.getImportdate());
		product.setExpirationdate(productDto.getExpirationdate());
		product.setQuantity(productDto.getQuantity());
		product.setUnit(productDto.getUnit());
		product.setPrice(productDto.getPrice());
		
		product.setImages(storageFileName);
		
		repo.save(product);
		
	    return "redirect:/products";
	}
	
	@GetMapping({"/edit"})
	public String showEditProductPage(Model model, @RequestParam int productid) {
	    try {
	    	Product product = repo.findById(productid).get();
	    	model.addAttribute("product",product);
	    	model.addAttribute("categories", repoCategory.findAll());
	    	
	    	ProductDto productDto = new ProductDto();

	    	productDto.setProductname(product.getProductname());
	    	productDto.setCategoryid(product.getCategoryid());
	    	productDto.setImportdate(product.getImportdate());
	    	productDto.setExpirationdate(product.getExpirationdate());
	    	productDto.setQuantity(product.getQuantity());
	    	productDto.setUnit(product.getUnit());
	    	productDto.setPrice(product.getPrice());
			
			model.addAttribute("productDto",productDto);
			
	    }
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "products/EditProduct";
	}
	@PostMapping({"/edit"})
	public String EditProduct(Model model, @RequestParam int productid,
			@Valid @ModelAttribute ProductDto productDto,
			BindingResult result) {
	    try {
	    	Product product = repo.findById(productid).get();
	    	model.addAttribute("product",product);
			if (result.hasErrors()){
				 model.addAttribute("categories", repoCategory.findAll());
				 return "products/CreateProduct";
			}	
			if(!productDto.getImages().isEmpty()) {
				String uploadDir = "public/images";
				Path oldImagePath = Paths.get(uploadDir + product.getImages());
				
				try {
					Files.delete(oldImagePath);
				}
				catch(Exception ex) {
			    	 System.out.println("Exception: " + ex.getMessage());
			    }
				MultipartFile image = productDto.getImages();
				Date createdAt = new Date(0);
				String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
				
				 try (InputStream inputStream = image.getInputStream()) {
				        Files.copy(inputStream, Paths.get(uploadDir + storageFileName), 
				            StandardCopyOption.REPLACE_EXISTING);
				 }
				 product.setImages(storageFileName); 
			}
			product.setProductname(productDto.getProductname());
			product.setCategoryid(productDto.getCategoryid());
			product.setImportdate(productDto.getImportdate());
			product.setExpirationdate(productDto.getExpirationdate());
			product.setQuantity(productDto.getQuantity());
			product.setUnit(productDto.getUnit());
			product.setPrice(productDto.getPrice());
			
			repo.save(product);
		}
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "redirect:/products";
	}
	@GetMapping({"/delete"})
	public String DeleteProduct( @RequestParam int productid) {
	    try {
	    	Product product = repo.findById(productid).get();
			repo.delete(product);
	    }
	    catch(Exception ex) {
	    	 System.out.println("Exception: " + ex.getMessage());
	    }
	    return "redirect:/products";
	}
}
