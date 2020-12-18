package com.sales.online.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.sales.online.controller.LoadDataComponent;
import com.sales.online.model.Item;
import com.sales.online.model.Subasta;
import com.sales.online.model.User;
import com.sales.online.repository.ItemRepository;
import com.sales.online.repository.SubastaRepository;
import com.sales.online.repository.UserRepository;

@Service
public class ItemService {

  private ItemRepository itemRepository;
  private SubastaRepository subastaRepository;
  private UserRepository userRepository;

  public ItemService(
      ItemRepository itemRepository,
      SubastaRepository subastaRepository,
      UserRepository userRepository) {
    this.itemRepository = itemRepository;
    this.subastaRepository = subastaRepository;
    this.userRepository = userRepository;
  }

  public void makeAnOffer(int userId, long itemId, float offer) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Item> optionalItem = itemRepository.findById(itemId);
    if (optionalUser.isPresent() && optionalItem.isPresent()) {
      Subasta subasta = new Subasta(optionalUser.get(), optionalItem.get(), offer);
      subastaRepository.save(subasta);
      optionalItem.get().setLatestPrice(offer);
      itemRepository.save(optionalItem.get());
    } else {
      throw new IllegalStateException("No se encontraró el Usuario o Producto a pujar");
    }
  }

  public Optional<Item> findById(long id) {
    return itemRepository
        .findById(id)
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              item.setLatestPrice(getLatestItemPrice(item));
              return item;
            });
  }

  public List<Item> getLatestItems() {
    return itemRepository
        .findLatestItems()
        .stream()
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              item.setLatestPrice(getLatestItemPrice(item));
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public Item save(Item item) {
    return itemRepository.save(item);
  }

  public Item findItem(String name) {
    return itemRepository.itemNew(name);
  }

  public List<Item> getNextToFinishItems() {
    return itemRepository
        .findNextToFinishItems()
        .stream()
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              item.setLatestPrice(getLatestItemPrice(item));
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public void updateExpiredItemToInactive() {
    List<Item> items = itemRepository.findExpiredItems();
    System.out.println("Size: " + items.size());
    items
        .stream()
        .map(
            (item) -> {
              item.setStatus("INACTIVO");
              itemRepository.save(item);
              return item;
            }).collect(Collectors.toCollection(ArrayList::new));
  }

  public List<Item> getBestRatedItems() {
    return itemRepository
        .findBestRatedItems()
        .stream()
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              item.setLatestPrice(getLatestItemPrice(item));
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public float getLatestItemPrice(Item item) {
    List<Subasta> subastas = subastaRepository.findLatestPrice(item.getId());
    if (!subastas.isEmpty()) {
      return subastas.get(0).getOffer();
    } else {
      return item.getStartingPrice();
    }
  }
}
