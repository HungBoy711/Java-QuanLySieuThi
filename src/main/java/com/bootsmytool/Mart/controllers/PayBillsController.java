package com.bootsmytool.Mart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bootsmytool.Mart.models.Category;
import com.bootsmytool.Mart.models.Product;
import com.bootsmytool.Mart.models.ProductCategory;
import com.bootsmytool.Mart.services.CategoryRepository;
import com.bootsmytool.Mart.services.ProductCategoryRepository;
import com.bootsmytool.Mart.services.ProductRepository;

@Controller
@RequestMapping("/paybills")
public class PayBillsController {
	@Autowired
	private CategoryRepository repoCategory;

	@Autowired
	private ProductCategoryRepository repoProductCat;

	@GetMapping("")
	public String showProductList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "9") int size, @RequestParam(required = false) String categoryname,
			Model model) {

		List<Category> categoryList = repoCategory.findAll();

		Page<ProductCategory> productPage;

		if (categoryname != null && !categoryname.isEmpty()) {

			productPage = repoProductCat.findByCategoryname(categoryname, PageRequest.of(page, size));
		} else {

			productPage = repoProductCat.findAll(PageRequest.of(page, size));
		}

		model.addAttribute("categoryList", categoryList);
		model.addAttribute("productPage", productPage);
		model.addAttribute("currentCategoryName", categoryname);

		return "paybills/index";
	}

}
