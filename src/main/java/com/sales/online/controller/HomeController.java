package com.sales.online.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sales.online.model.UserLogin;
import com.sales.online.service.ItemService;

@Controller
public class HomeController {

	private ItemService itemService;
	private String rememberMeUser;
	private String rememberMePassword;

	public HomeController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping({ "/", "index" })
	public String index(@ModelAttribute(name = "userData") UserLogin userData, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attributes) {
		model.addAttribute("latestItems", itemService.getLatestItems());
		model.addAttribute("bestRatedItems", itemService.getBestRatedItems());
		model.addAttribute("nextToFinishItems", itemService.getNextToFinishItems());
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String[] usuario = cookies[1].getValue().split("-");
			if (!usuario[0].equals("null") && !usuario[1].equals("null")) {
				UserLogin u = new UserLogin();
				u.setName(usuario[1]);
				model.addAttribute("userData", u);
				return "index";
			}
		}

		HttpSession session = request.getSession();
		if (session != null && userData != null) {
			response.addCookie(
					new Cookie("user", userData.getId() + "-" + userData.getName() + "-" + userData.getPassword()));
			model.addAttribute("userData", userData);
		}

		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login-register";
	}

	public String getRememberMeUser() {
		return rememberMeUser;
	}

	public void setRememberMeUser(String rememberMeUser) {
		this.rememberMeUser = rememberMeUser;
	}

	public String getRememberMePassword() {
		return rememberMePassword;
	}

	public void setRememberMePassword(String rememberMePassword) {
		this.rememberMePassword = rememberMePassword;
	}

}
