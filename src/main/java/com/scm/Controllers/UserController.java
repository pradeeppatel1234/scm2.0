package com.scm.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	//user dashboard
	@GetMapping("/dashboard")
	public String userDashboard() {
		return "user/dashboard";
	}
	
	//user profile page
		@GetMapping("/profile")
		public String userProfile() {
			return "user/profile";
		}
	
	//user add contacts page
	
	//userview contact
	
	//user edit contact
	
	//user delete contact
	
	//user search contact
}
