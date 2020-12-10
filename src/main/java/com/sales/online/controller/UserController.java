package com.sales.online.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.env.Environment;
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

import com.sales.online.model.EmailTemplate;
import com.sales.online.model.User;
import com.sales.online.model.UserLogin;
import com.sales.online.service.EmailService;
import com.sales.online.service.UserService;

import freemarker.template.TemplateException;

@Controller
public class UserController {

  private final UserService userService;
  private final EmailService emailService;
  private final Environment env;

  public UserController(UserService userService, EmailService emailService, Environment env) {
    this.userService = userService;
    this.emailService = emailService;
    this.env = env;
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

  @GetMapping("/users/logInUser")
  public String viewlogInUser(@ModelAttribute(name = "userData") UserLogin userData, Model model) {
	  return "log-in";
  }
  
  @PostMapping("/users/logInUser")
   public String logInUser(@ModelAttribute(name = "userData") UserLogin userData, Model model) {
	  User user=userService.findUserByEmailAndPassword(userData.getEmail(), userData.getPassword());
	  if(user!=null) {
		  System.out.println(user.getName());
		  return "redirect:/index";
	  }else {
		  model.addAttribute("notFound", "El correo o la contraseña incorrectos");
		  return "log-in";  
	  }
	  
	  
	  
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
      try {
        if (userData.getPassword().equals(userData.getConfirmPassword())) {
          userService.save(userData);
          sendMail(userData);
          redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado");
        } else {
          model.addAttribute("mensaje", "Las contraseñas no son iguales");
          return "addUser";
        }

      } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("mensaje", "Error al guardar el usuario: " + e.getMessage());
        return "addUser";
      }
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
      if (userData.getPassword().equals(userData.getConfirmPassword())) {
        userService.save(userData);
        atts.addFlashAttribute("mensaje", "Usuario actualizado");
      } else {
        model.addAttribute("mensaje", "Las contraseñas no son iguales");
        return "addUser";
      }
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

  private void sendMail(User user) throws MessagingException, IOException, TemplateException {
    Map<String, Object> model = new HashMap<>();
    model.put("name", user.getName());

    EmailTemplate emailTemplate =
        new EmailTemplate(
            user.getEmail(),
            env.getProperty("mail.create.user.subject"),
            env.getProperty("mail.create.user.template"),
            model);

    emailService.sendMessage(emailTemplate);
  }
}
