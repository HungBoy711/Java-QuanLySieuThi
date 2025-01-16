package com.bootsmytool.Mart.models;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class StaffDto {
	@NotEmpty(message="Tên không được để trống")
	private String staffname;
	
	private Date birthdate;
	
	@NotEmpty(message="Giới tính không được để trống")
	private String gender;
	
	@NotEmpty(message="Quê quán không được để trống")
	private String hometown;
	
	@Size(min = 10, message="Số điện thoại phải tối đa 10 số")
	@Size(max = 11, message="Số điện thoại không quá 10 số")
	private String phone;
	
	@NotEmpty(message="Email không được để trống")
	private String email;
	
	@NotEmpty(message="Chức vụ không được để trống")
	private String position;
	
	private MultipartFile imagename;

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public MultipartFile getImagename() {
		return imagename;
	}

	public void setImagename(MultipartFile imagename) {
		this.imagename = imagename;
	}
	
	
}
