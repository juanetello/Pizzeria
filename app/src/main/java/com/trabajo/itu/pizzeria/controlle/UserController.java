package com.trabajo.itu.pizzeria.controlle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trabajo.itu.pizzeria.entity.UserPizzeria;
import com.trabajo.itu.pizzeria.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public String profile(ModelMap modelMap, @RequestParam String id) {
		UserPizzeria userPizzeria = userService.findUserById(id);
		modelMap.put("userPizzeria", userPizzeria);
	
		
		return "profile.html";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(ModelMap modelMap, @RequestParam String id, @RequestParam String name, 
			@RequestParam String surname, @RequestParam String mail, @RequestParam String password1, 
			@RequestParam String password2 ) {
		try {
			UserPizzeria userPizzeria = userService.updateUser(id, name, surname, mail, password1, password2);
			modelMap.put("userPizzeria", userPizzeria);
			return "profile.html";
		} catch (Exception e) {
			modelMap.put("error", e.getMessage());
			
			UserPizzeria userPizzeria = new UserPizzeria();
			userPizzeria.setName(name);
			userPizzeria.setSurname(surname);
			userPizzeria.setMail(mail);
			userPizzeria.setId(id);
			userPizzeria.setPassword(password1);
			
			modelMap.put("userPizzeria", userPizzeria);
			
			return "profile.html";
		}
	}
	
	@PostMapping("/register")
	public String register(ModelMap modelMap, @RequestParam String name, 
			@RequestParam String surname, @RequestParam String mail, @RequestParam String password1, 
			@RequestParam String password2 ) {
		try {
			UserPizzeria userPizzeria = userService.register(name, surname, mail, password1, password2);
			modelMap.put("userPizzeria", userPizzeria);
			return "index.html";
		} catch (Exception e) {
			modelMap.put("error", e.getMessage());
			
			modelMap.put("nombre", name);
			modelMap.put("surname", surname);
			modelMap.put("mail", mail);
			modelMap.put("password1", password1);
			modelMap.put("password2", password2);
			
			return "profile.html";
		}
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	
}
