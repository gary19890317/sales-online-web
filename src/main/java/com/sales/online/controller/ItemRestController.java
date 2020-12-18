package com.sales.online.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.online.model.Item;
import com.sales.online.model.ItemData;
import com.sales.online.model.PujarData;
import com.sales.online.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemRestController {

  private ItemService itemService;

  public ItemRestController(ItemService itemService) {
    this.itemService = itemService;
  }

  @PostMapping("pujar")
  public ResponseEntity<PujarData> pujar(@RequestBody PujarData pujarData) {
    Optional<Item> optionalItem = itemService.findById(pujarData.getItemId());
    try {
      if (optionalItem.isPresent()) {
        Item item = optionalItem.get();
        itemService.makeAnOffer(pujarData.getUserId(), pujarData.getItemId(), pujarData.getPuja());
        pujarData.setLatestPrice(itemService.getLatestItemPrice(item));
        return new ResponseEntity<>(pujarData, HttpStatus.OK);
      } else {
        pujarData.setError("No se encontró el producto seleccionado");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      e.printStackTrace();
      pujarData.setError("No fue posible realizar la oferta/puja");
      return new ResponseEntity<>(pujarData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("item/{id}")
  public ResponseEntity<ItemData> getItemInformation(@PathVariable Long id) {
    Optional<Item> itemOptional = itemService.findById(id);
    if (itemOptional.isPresent()) {
      Item item = itemOptional.get();
      item.setLatestPrice(itemService.getLatestItemPrice(item));
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
