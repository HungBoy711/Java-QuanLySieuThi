package com.bootsmytool.Mart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootsmytool.Mart.services.ProductRepository;
import com.bootsmytool.Mart.services.StaffRepository;
import com.bootsmytool.Mart.services.UserRepository;

@Controller
@RequestMapping("/homes")
public class HomeController {
    @Autowired
    private StaffRepository repoStaff;

    @Autowired
    private ProductRepository repoProduct;

    @Autowired
    private UserRepository repoUser;

    @GetMapping("")
    public String showLayout(Model model) {
        model.addAttribute("staffCount", repoStaff.count());
        model.addAttribute("productCount", repoProduct.count());
        model.addAttribute("userCount", repoUser.count());
        return "homes/index"; 
    }
}
