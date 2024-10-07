package com.lemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lemon.model.User;
import com.lemon.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class MainController {
	@Autowired
	UserRepository userRepository;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

//	@GetMapping("/csrf-token")
//	@ResponseBody
//	public CsrfToken getCsrfToken( HttpServletRequest request) {
//		return (CsrfToken) request.getAttribute("_csrf");
//	}


	 // Serve login page
    @GetMapping("/login")
    public String loginPage() {
        return "Login";  // This will serve your login.html page (or whichever view you are using)
    }
    
    // Serve registration page
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "Register";  // This will serve your register.html page
    }
    
    // Handle user registration form submit
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
    	user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
    
    // Home or landing page after login
    @GetMapping("/")
    public String homePage(HttpServletRequest request) {
    	  HttpSession session = request.getSession(false);
    	    if (session != null) {
    	        System.out.println("Session ID: " + session.getId());
    	        // Optionally log other session attributes
    	    }
        return "AdminDashboard";  // This will serve your home page view (like home.html)
    }
    
    @GetMapping("/member")
    public String homesdfPage() {
        return "MemberListNew";  // This will serve your home page view (like home.html)
    }
	
	
}
