package com.scm.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;

import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller

public class controller {

	@Autowired
	private UserServices userService;
	

	@GetMapping("/")
	public String index() {
		System.out.println("this is handler");
		return "redirect:/home";
	}
	
	
	
	@GetMapping("/home")
	public String home() {
		System.out.println("this is handler");
		return "home";
	}
	
	
	@GetMapping("/services")
	public String services() {
		System.out.println("this is handler");
		return "services";
	}
	
	
	@GetMapping("/about")
	public String about() {
		System.out.println("this is handler");
		return "about";
	}
	
	
	@GetMapping("/contact")
	public String contact() {
		System.out.println("this is handler");
		return "contact";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("this is handler");
		return "login";
	}
	
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String register(Model model) {
		
		
		
		//blank ob. signup page me send karenge taki ob me data automatic fill ho jaye
		UserForm userForm=new UserForm();
		//defaoult data bhi send kar sakte hai
	//	userForm.setName("pradeep");
	//	userForm.setPhoneNumber("7489622598");
		model.addAttribute("userForm",userForm);
		return "signup";
	}
	
	//processing register
	
	@PostMapping("/do_register")
	public String processRegister(@Valid @ModelAttribute UserForm userform, BindingResult rBindingResult, HttpSession session) {//is userform ob ke pass pura form ka data aa jayega jo attribute UserForm class me diye gye hai or signup me diye hai
		//fetch form data(form ka data/signup page ka data
		System.out.println(userform);
		
		//validate form data
		if(rBindingResult.hasErrors()) {
			return "signup";
		}
		
		//save to database
		
		//userservice
		
		//UserForm--->User
		
	/*	User user=User.builder()
		.name(userform.getName())
		.email(userform.getEmail())
		.password(userform.getPassword())
		.about(userform.getAbout())
		.phoneNumber(userform.getPhoneNumber())
		.profilePic("https://www.olacabs.com/")
		.build();*/
		
		User user=new User();
		user.setName(userform.getName());
		user.setEmail(userform.getEmail());
		user.setPassword(userform.getPassword());
		user.setAbout(userform.getAbout());
		user.setPhoneNumber(userform.getPhoneNumber());
		user.setProfilePic("https://www.olacabs.com/");
						
				
;		
    User saveduser=userService.saveUser(user);
    System.out.println(saveduser);
    System.out.println("user saved");
   
    //message="regestration successfull
    
    //add the message
    Message message=Message.builder().content("Regestration Successful").type(MessageType.green).build();
    session.setAttribute("message",message);
    
	//redirect login page
		return "redirect:/signup";
	}

	
}
