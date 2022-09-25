package com.example.library.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.library.entities.Admin;
import com.example.library.entities.Constants;
import com.example.library.service.HelperService;
import com.example.library.entities.Message;

@Controller
@RequestMapping("/app/**")
public class loginController {

	@Autowired
	HelperService helperService;

	public loginController() {
	}

	
	@GetMapping("/app/home")
	public String getBookById(Model model, HttpSession session) {

		model.addAttribute("title", "Home");
		return "Basic/home";
	}

	
	@GetMapping("/app/login")
	public String login(Model model, HttpSession session) {

		System.out.println("DEBUG...1\n");

		model.addAttribute("title", "Login");
		model.addAttribute("admin", new Admin());
		return "Basic/login";
	}

	
	@PostMapping("/app/logout")
	public String logout(Model model, HttpSession session) throws IOException, ServletException {
		session.removeAttribute(Constants.SESSION_ADMIN);

		model.addAttribute("title", "Login");
		model.addAttribute("admin", new Admin());
		return "Basic/login";
	}

	
	@PostMapping("/app/verify")
	public String dashboard(@ModelAttribute("admin") Admin admin, Model model, HttpSession session) {

		return helperService.verifyAdmin(admin, model, session);

	}

	
	@GetMapping("/app/dashboard")
	public String backToDashboard(Model model, HttpSession session) {

		if (checkSession(session).equals(Constants.SESSION_NOTOK)) {
			model.addAttribute("title", "Login");
			model.addAttribute("admin", new Admin());
			return "Basic/login";
		}

		model.addAttribute("title", "Dashboard");
		return "Basic/dashboard";
	}

	//session cheking
	private String checkSession(HttpSession session) {
		String sessionInfo = (String) session.getAttribute(Constants.SESSION_ADMIN);

		if (sessionInfo == null) {
			session.setAttribute("message", new Message("SESSION NOT VALID PLEASE LOGIN AGAIN!!!", "alert-danger"));
			return Constants.SESSION_NOTOK;
		} else {
			return Constants.SESSION_OK;
			
		}
	}

}
