package com.bootsmytool.Mart.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bootsmytool.Mart.models.User;
import com.bootsmytool.Mart.services.UserRepository;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showLoginPage(Model model) {
        return "logins/index";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("role") String role,
        Model model) {

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            if (role.equals(user.getRole())) {
                if ("Quản lý".equals(role)) {
                    return "redirect:/homes";
                } else if ("Nhân viên".equals(role)) {
                    return "redirect:/paybills";
                }
            } else {
                model.addAttribute("error", "Chức vụ không khớp với tài khoản");
                return "logins/index";
            }
        } else {
            model.addAttribute("error", "Tên tài khoản hoặc mật khẩu không đúng");
            return "logins/index";
        }
        
        return "logins/index"; 
    }

}

	
	
