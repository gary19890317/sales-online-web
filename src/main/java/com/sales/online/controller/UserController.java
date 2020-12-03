package com.sales.online.controller;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sales.online.model.User;
import com.sales.online.service.UserService;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  }

  @GetMapping("/users")
  public String showUsersView(Model model) {
    model.addAttribute("users", userService.findAll());
    return "users";
  }

  @PostMapping("/users/addUser")
  public String addUser(
      @ModelAttribute(name = "userData") @Valid User userData,
      BindingResult result,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "addUser";
    } else {
      userService.save(userData);
      redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado");
      return "redirect:/users/addUser";
    }
  }

  @GetMapping("/users/addUser")
  public String showAddUserView(@ModelAttribute(name = "userData") User userData, Model model) {
    return "addUser";
  }

  @PostMapping("/users/edit/{id}")
  public String updateUser(
      @ModelAttribute(name = "userData") @Valid User userData,
      BindingResult result,
      @PathVariable int id,
      Model model,
      RedirectAttributes atts) {
    if (result.hasErrors()) {
      return "editUser";
    } else {
      userService.save(userData);
      atts.addFlashAttribute("mensaje", "Usuario actualizado");
      return "redirect:/users";
    }
  }

  @GetMapping("/users/edit/{id}")
  public String showEditView(@PathVariable int id, Model model) {
    model.addAttribute("userData", userService.findById(id));
    return "editUser";
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable int id, Model model) {
    userService.deleteById(id);
    return "redirect:/users";
  }
}
