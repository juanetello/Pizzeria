package com.trabajo.itu.pizzeria.controlle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalController {

	@GetMapping("/")
	public String home() {
		return "index.html";
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@GetMapping("/loginsuccess")
	public String signIn() {
		return "index.html";
	}

	@GetMapping("/favoritos")
	public String favoritos() {
		return "favoritos.html";
	}

}
