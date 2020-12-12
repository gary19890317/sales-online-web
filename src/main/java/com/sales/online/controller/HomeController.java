package com.sales.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sales.online.model.UserLogin;
import com.sales.online.service.ItemService;

@Controller
public class HomeController {

  private ItemService itemService;

  public HomeController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping({"/", "index"})
  public String index(@ModelAttribute(name = "userData") UserLogin userData, Model model) {
    model.addAttribute("latestItems", itemService.getLatestItems());
    model.addAttribute("bestRatedItems", itemService.getBestRatedItems());
    model.addAttribute("nextToFinishItems", itemService.getNextToFinishItems());
    return "index";
  }
  
  @GetMapping("/login")
  public String login(Model model) {
	  return "login-register";
  }
}
