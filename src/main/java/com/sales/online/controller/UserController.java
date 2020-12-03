package com.sales.online.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String addUser(@Valid @ModelAttribute User userData,BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		
		if (result.hasErrors()) {
			List<FieldError> lista=result.getFieldErrors();
			redirectAttributes=verifyField(redirectAttributes,lista);
			return "redirect:/users/addUser";
		}else {
			System.out.println("Entra al else");
			userService.save(userData);
			model.addAttribute("userData", userData);
			redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado");
			return "redirect:/users/addUser";
		}
	}

  private RedirectAttributes verifyField(RedirectAttributes redirectAttributes, List<FieldError> lista) {
	// TODO Auto-generated method stub
	  
	  for (FieldError fieldError : lista) {
			System.out.println(fieldError.getField());
			
			switch (fieldError.getField()) {
			case "name":
			redirectAttributes.addFlashAttribute("nameError", "Rellenar el campo nombre");
				break; 
			case "address":
				redirectAttributes.addFlashAttribute("addressError", "Rellenar el campo de dirección");
				break;
			case "email":
				redirectAttributes.addFlashAttribute("emailError", "Rellenar el campo correo");
				break;
			}
		}
	return redirectAttributes;
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
