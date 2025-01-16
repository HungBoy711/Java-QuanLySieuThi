package com.bootsmytool.Mart.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SupplierDto {
	
	@NotEmpty(message="Tên nhà cung cấp không được để trống")
	private String suppliername;
	
	@Size(min = 10, message="Số điện thoại phải tối đa 10 số")
	@Size(max = 11, message="Số điện thoại không quá 10 số")
	private String phone;
	
	@NotEmpty(message="Email không được để trống")
	private String email;
	
	@NotEmpty(message="Địa chỉ không được để trống")
	private String address;
	
	@NotEmpty(message="Thành phố không được để trống")
	private String City;
	
	@NotEmpty(message="Tỉnh thành không được để trống")
	private String province;

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	
}
