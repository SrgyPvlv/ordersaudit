package com.example.sergey.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.sergey.Service.UsersService;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private UsersService userservice;
	
	@ModelAttribute("userLogin")
	public String getUserLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login=auth.getName();
	    return login;
	  }
	
	@ModelAttribute("userRole")
	public String getUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login=auth.getName();
		if (login.equals("anonymousUser")) return " "; else {
		String role=userservice.findUsersByLogin(login).getRole();
		return role;}
	  }
}
