package com.sales.online.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.online.model.Item;
import com.sales.online.model.ItemData;
import com.sales.online.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemRestController {

  private ItemService itemService;

  public ItemRestController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("item/{id}")
  public ResponseEntity<ItemData> getItemInformation(@PathVariable Long id) {
    Optional<Item> itemOptional = itemService.findById(id);
    if (itemOptional.isPresent()) {
      Item item = itemOptional.get();

      ItemData data =
          new ItemData(
              id,
              item.getName(),
              item.getPicture(),
              item.getStartingPrice(),
              item.getLatestPrice(),
              item.getDescription(),
              item.getExpirationDate().toString(),
              item.getStatus(),
              item.getRanking(),
              item.getPictureBase64());
      return new ResponseEntity<>(data, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
