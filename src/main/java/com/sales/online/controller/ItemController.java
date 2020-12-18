package com.sales.online.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.zip.Deflater;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sales.online.model.Item;
import com.sales.online.model.User;
import com.sales.online.model.UserLogin;
import com.sales.online.service.ItemService;
import com.sales.online.service.UserService;

@Controller
public class ItemController {
  private ItemService itemService;
  private UserService userService;

  public ItemController(ItemService itemService, UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
  }

  @PostMapping("/upload")
  public String uplaodImage(
      @ModelAttribute(name = "imgData") Item itemData,
      @RequestParam("imageFile") MultipartFile file,
      HttpSession httpSession,
      Model model)
      throws IOException {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      java.util.Date parsedDate = dateFormat.parse(itemData.getExpirationDate_aux());
      Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
      itemData.setExpirationDate(timestamp);
      Item item =
          new Item(
              itemData.getName(),
              compressBytes(file.getBytes()),
              itemData.getDescription(),
              itemData.getStartingPrice(),
              itemData.getExpirationDate(),
              itemData.getStatus(),
              0);
      UserLogin userLogin = (UserLogin) httpSession.getAttribute("userLogged");
      if (userLogin != null) {
        User user = userService.findById(userLogin.getId());
        item.setUser(user);
        itemService.save(item);
      } else {
        model.addAttribute("mensaje", "No se ha iniciado sessión");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    model.addAttribute("mensaje", "Se guardó de manera exitosa");
    return "addItem";
  }

  @GetMapping("/addItem")
  public String showViewUploadImage(@ModelAttribute(name = "imgData") Item itemData, Model model) {
    return "addItem";
  }

  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return outputStream.toByteArray();
  }
}
