package com.sales.online.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  public String index(
      Model model,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "category", required = false) String category,
      HttpSession httpSession) {
    model.addAttribute(
        "latestItems", itemService.getLatestItems(setupParameter(name), setupParameter(category)));
    model.addAttribute(
        "bestRatedItems",
        itemService.getBestRatedItems(setupParameter(name), setupParameter(category)));
    model.addAttribute(
        "nextToFinishItems",
        itemService.getNextToFinishItems(setupParameter(name), setupParameter(category)));
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

  private String setupParameter(String data) {
    return data == null ? "" : data;
  }
}
