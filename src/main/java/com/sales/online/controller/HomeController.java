package com.sales.online.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

  @GetMapping({"/", "index"})
  public String index(Model model, HttpSession httpSession) {
    model.addAttribute("latestItems", itemService.getLatestItems());
    model.addAttribute("bestRatedItems", itemService.getBestRatedItems());
    model.addAttribute("nextToFinishItems", itemService.getNextToFinishItems());
    UserLogin userLogin = (UserLogin) httpSession.getAttribute("userLogged");
    if (userLogin != null) {
    	model.addAttribute("car", itemService.getCarItems(userLogin.getId()));
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
