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

import com.bootsmytool.Mart.models.User;
import com.bootsmytool.Mart.services.UserRepository;

@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UserRepository repo;

	@GetMapping("")
	public String showUserList(
	        @RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "5") int size, 
	        Model model) {
	    Page<User> userPage = repo.findAll(PageRequest.of(page, size, Sort.by("userid").descending()));
	    model.addAttribute("userPage", userPage);
	    return "users/index";
	}
}
