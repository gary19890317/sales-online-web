package com.sales.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sales.online.model.User;
import com.sales.online.service.UserService;

@Controller 
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public String showUsersView(Model model) {
    model.addAttribute("users", userService.findAll());
    return "users";
  }

  /**
 * @param userData
 * @param model
 * @return
 */ 
@PostMapping("/users/addUser")
  public String addUser(@ModelAttribute User userData, Model model, RedirectAttributes redirectAttributes) {
	userService.save(userData);
    model.addAttribute("userData", userData);
    redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado");
    
    return "redirect:/users/addUser";
  }

  @GetMapping("/users/addUser")
  public String showAddUserView(@ModelAttribute User userData, Model model) {
    model.addAttribute("userData", userData);
    return "addUser";
  }

  
  @PostMapping("/users/edit/{id}")
  public String updateUser(@ModelAttribute User userData, @PathVariable int id, Model model,RedirectAttributes atts) {
    userService.save(userData);
    model.addAttribute("userData", userData);
    model.addAttribute("userId", id);
    atts.addFlashAttribute("mensaje", "Usuario actualizado");
    
    return "redirect:/users";
  }

  @GetMapping("/users/edit/{id}")
  public String showEditView(@PathVariable int id, Model model) {
    model.addAttribute("userData", userService.findById(id));
    model.addAttribute("userId", id);
    return "editUser";
  }

  @GetMapping("/users/delete/{id}")
  public String deleteUser(@PathVariable int id, Model model) {
    userService.deleteById(id);
    model.addAttribute("isNewUser", false);
    return "redirect:/users";
  }
}
