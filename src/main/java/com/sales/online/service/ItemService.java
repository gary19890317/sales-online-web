package com.sales.online.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.sales.online.controller.LoadDataComponent;
import com.sales.online.model.Item;
import com.sales.online.repository.ItemRepository;

@Service
public class ItemService {

  private ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public Optional<Item> findById(long id) {
    return itemRepository
        .findById(id)
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
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
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public List<Item> getBestRatedItems() {
    return itemRepository
        .findBestRatedItems()
        .stream()
        .map(
            (item) -> {
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
