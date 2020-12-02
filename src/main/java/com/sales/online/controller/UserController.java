package com.sales.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sales.online.model.User;
import com.sales.online.service.UserService;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/addUser")
  public String addUser(Model model) {
    return "redirect:/users";
  }

  @GetMapping("/users")
  public String showUsers(Model model) {
    model.addAttribute("users", userService.findAll());
    if(model.getAttribute("isNewUser") == null) {
		model.addAttribute("userData", new User());
		model.addAttribute("userId", -1);
		model.addAttribute("isNewUser", false);
    }
    return "users";
  }

  @GetMapping("/users/edit/{id}")
  public String editUser(@PathVariable int id, Model model) {
    model.addAttribute("userData", userService.findById(id));
    model.addAttribute("userId", id);
    model.addAttribute("isNewUser", false);
    return "editUser";
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable int id, Model model) {
    userService.deleteById(id);
    model.addAttribute("isNewUser", false);
    return "redirect:/users";
  }
}
