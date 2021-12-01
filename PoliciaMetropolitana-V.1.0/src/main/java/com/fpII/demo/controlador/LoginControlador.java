package com.fpII.demo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlador {
	
	@GetMapping({"/","/login"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/principal")
	public String principal(ModelMap model) {
		return "menu/principal";
	}
	
	@GetMapping("/menu_personas")
	public String menu_personas(ModelMap model) {
		return "menu/menu_personas";
	}
	
	@GetMapping("/menu_bancos")
	public String menu_bancos(ModelMap model) {
		return "menu/menu_bancos";
	}
	
	@GetMapping("/menu_delincuencia")
	public String menu_delincuencia(ModelMap model) {
		return "menu/menu_delincuencia";
	}
}