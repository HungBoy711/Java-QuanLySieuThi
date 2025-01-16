package com.bootsmytool.Mart.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/histories")
public class HistoriesController {
	@GetMapping("")
	public String showHistories() {
		return "histories/index";
	}

}
