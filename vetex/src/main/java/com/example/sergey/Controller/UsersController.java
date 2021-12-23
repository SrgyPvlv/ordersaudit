package com.example.sergey.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sergey.Model.Users;
import com.example.sergey.Service.UsersService;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping("/admin/usersShow") //список всех пользователей
	public String showUsers(Model model) {
		List<Users> users=usersService.findAllUsers();
		model.addAttribute("users", users);
		return "users";
	}
	
	@GetMapping("/admin/usersCreate") // переход на форму создания нового пользователя
	public String newUserForm() {	   
	   return "newUserForm";
	}
	
	@PostMapping("/admin/usersCreate") // создание нового пользователя
	public String createNewUser(@RequestParam("login") String login, @RequestParam("password") String password,@RequestParam("role") String role,
			@RequestParam("fullname") String fullname) {	   
	   Users newUser=new Users(login,password,role,fullname);
	   usersService.saveUsers(newUser);
		
		return "redirect:/admin/usersShow";
	}
	
	@GetMapping("/admin/usersEdit") // переход на форму редактирования пользователя
	public String editUserForm(@RequestParam("id") int id, Model model) {	   
		Users user=usersService.findUsersById(id);
		String fullname=user.getFullName();
		model.addAttribute("user", user);
		model.addAttribute("fullname", fullname);
		
		return "editUserForm";
	}
	
	@PostMapping("/admin/usersEdit") // редактирование пользователя
	public String editNewUser(@RequestParam("id") int id,@RequestParam("login") String login, @RequestParam("password") String password,@RequestParam("role") String role,
			@RequestParam("fullname") String fullname) {	   
	   Users user=usersService.findUsersById(id);
	   user.setLogin(login);
	   user.setPassword(password);
	   user.setRole(role);
	   user.setFullName(fullname);
	   usersService.saveUsers(user);
		
		return "redirect:/admin/usersShow";
	}
	
	@GetMapping("/admin/usersDelete") // удаление пользователя
	public String deleteUser(@RequestParam("id") int id) {
		usersService.deleteUserById(id);
		
		return "redirect:/admin/usersShow";
	}
}