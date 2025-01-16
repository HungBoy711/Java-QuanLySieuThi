package com.bootsmytool.Mart.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;

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


import com.bootsmytool.Mart.models.Staff;
import com.bootsmytool.Mart.models.StaffDto;
import com.bootsmytool.Mart.services.StaffRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/staffs")
public class StaffsController {

		@Autowired
		private StaffRepository repo;
		
		
		@GetMapping("")
		public String showStaffList(
	            @RequestParam(defaultValue = "0") int page, 
	            @RequestParam(defaultValue = "5") int size, 
	            Model model) {
	        Page<Staff> staffPage = repo.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
	        model.addAttribute("staffPage", staffPage);
	        return "staffs/index";
	    }
		
		@GetMapping({"/create"})
		public String showCreateStaffPage(Model model) {
		    StaffDto staffDto = new StaffDto();
		    model.addAttribute("staffDto", staffDto);
		    return "staffs/CreateStaff";
		}
		
		@PostMapping({"/create"})
		public String CreateStaff(
				@Valid @ModelAttribute StaffDto staffDto,
				BindingResult result
				) {
			if (staffDto.getImagename().isEmpty()) {
				result.addError(new FieldError("StaffDto", "imagename", "Yeu cau anh"));
			}
			
			if (result.hasErrors()) {
				return "staffs/CreateStaff";
			}
			MultipartFile image = staffDto.getImagename();
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
			
			Staff staff = new Staff();
			staff.setStaffname(staffDto.getStaffname());
			staff.setBirthdate(staffDto.getBirthdate());
			staff.setGender(staffDto.getGender());
			staff.setHometown(staffDto.getHometown());
			staff.setPhone(staffDto.getPhone());
			staff.setEmail(staffDto.getEmail());
			staff.setPosition(staffDto.getPosition());
			staff.setImagename(storageFileName);
			
			repo.save(staff);
			
		    return "redirect:/staffs";
		}
		
		@GetMapping({"/edit"})
		public String showEditStaffPage(Model model, @RequestParam int id) {
		    try {
		    	Staff staff = repo.findById(id).get();
		    	model.addAttribute("staff",staff);
		    	
		    	StaffDto staffDto = new StaffDto();
		    	staffDto.setStaffname(staff.getStaffname());
				staffDto.setBirthdate(staff.getBirthdate());
				staffDto.setGender(staff.getGender());
				staffDto.setHometown(staff.getHometown());
				staffDto.setPhone(staff.getPhone());
				staffDto.setEmail(staff.getEmail());
				staffDto.setPosition(staff.getPosition());
				
				model.addAttribute("staffDto",staffDto);
				
		    }
		    catch(Exception ex) {
		    	 System.out.println("Exception: " + ex.getMessage());
		    }
		    return "staffs/EditStaff";
		}
		@PostMapping({"/edit"})
		public String EditStaff(Model model, @RequestParam int id,
				@Valid @ModelAttribute StaffDto staffDto,
				BindingResult result) {
		    try {
		    	Staff staff = repo.findById(id).get();
		    	model.addAttribute("staff",staff);
				if (result.hasErrors()){
					return "staffs/EditStaff";
				}	
				if(!staffDto.getImagename().isEmpty()) {
					String uploadDir = "public/images";
					Path oldImagePath = Paths.get(uploadDir + staff.getImagename());
					
					try {
						Files.delete(oldImagePath);
					}
					catch(Exception ex) {
				    	 System.out.println("Exception: " + ex.getMessage());
				    }
					MultipartFile image = staffDto.getImagename();
					Date createdAt = new Date(0);
					String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
					
					 try (InputStream inputStream = image.getInputStream()) {
					        Files.copy(inputStream, Paths.get(uploadDir + storageFileName), 
					            StandardCopyOption.REPLACE_EXISTING);
					 }
					 staff.setImagename(storageFileName); 
				}
				staff.setStaffname(staffDto.getStaffname());
				staff.setBirthdate(staffDto.getBirthdate());
				staff.setGender(staffDto.getGender());
				staff.setHometown(staffDto.getHometown());
				staff.setPhone(staffDto.getPhone());
				staff.setEmail(staffDto.getEmail());
				staff.setPosition(staffDto.getPosition());
				
				repo.save(staff);
			}
		    catch(Exception ex) {
		    	 System.out.println("Exception: " + ex.getMessage());
		    }
		    return "redirect:/staffs";
		}
		
		@GetMapping({"/delete"})
		public String DeleteStaff( @RequestParam int id) {
		    try {
		    	Staff staff = repo.findById(id).get();
				repo.delete(staff);
		    }
		    catch(Exception ex) {
		    	 System.out.println("Exception: " + ex.getMessage());
		    }
		    return "redirect:/staffs";
		}
				
}
