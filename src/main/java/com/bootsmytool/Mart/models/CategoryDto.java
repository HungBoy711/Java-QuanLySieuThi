package com.bootsmytool.Mart.models;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {

	@NotEmpty(message="Tên sản phẩm không được để trống")
	private String categoryname;
	
	@NotEmpty(message="Mô tả không được để trống")
	private String description;

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
