package com.sales.online.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.online.controller.LoadDataComponent;
import com.sales.online.model.EmailTemplate;
import com.sales.online.model.Item;
import com.sales.online.model.Ranking;
import com.sales.online.model.Subasta;
import com.sales.online.model.User;
import com.sales.online.repository.ItemRepository;
import com.sales.online.repository.RankingRepository;
import com.sales.online.repository.SubastaRepository;
import com.sales.online.repository.UserRepository;

import freemarker.template.TemplateException;

@Service
@Transactional
public class ItemService {

  private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

  private ItemRepository itemRepository;
  private SubastaRepository subastaRepository;
  private UserRepository userRepository;
  private RankingRepository rankingRepository;
  private EmailService emailService;
  private Environment env;

  public ItemService(
      ItemRepository itemRepository,
      SubastaRepository subastaRepository,
      UserRepository userRepository,
      RankingRepository rankingRepository,
      EmailService emailService,
      Environment env) {
    this.itemRepository = itemRepository;
    this.subastaRepository = subastaRepository;
    this.userRepository = userRepository;
    this.rankingRepository = rankingRepository;
    this.emailService = emailService;
    this.env = env;
  }

  public void rank(int userId, long itemId, int stars) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Item> optionalItem = itemRepository.findById(itemId);
    if (optionalUser.isPresent() && optionalItem.isPresent()) {
      Ranking ranking = new Ranking();
      ranking.setItem(optionalItem.get());
      ranking.setUser(optionalUser.get());
      ranking.setStars(stars);
      ranking.setCreated(Timestamp.valueOf(LocalDateTime.now()));
      rankingRepository.save(ranking);
    } else {
      throw new IllegalStateException("No se encontraró el Usuario o Producto a calificar");
    }
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
              item.setRanking(rankingRepository.getAvgRanking(item.getId()));
              return item;
            });
  }

  public List<Item> getLatestItems(String name, String category) {
    if (!name.isEmpty() && !category.isEmpty()) {
      return itemRepository
          .findLatestItemsByNameAndCategory(name, category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else if (!category.isEmpty() && name.isEmpty()) {
      return itemRepository
          .findLatestItemsByCategory(category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else if (!name.isEmpty() && category.isEmpty()) {
      return itemRepository
          .findLatestItemsByName(name)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else {
      return itemRepository
          .findLatestItems()
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    }
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
              item.setRanking(rankingRepository.getAvgRanking(item.getId()));
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

  public List<Item> getCarItems(int userId) {
    return subastaRepository
        .findSubastaByUser(userId)
        .stream()
        .map(
            (subasta) -> {
              Item item = subasta.getItem();
              byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
              item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
              item.setLatestPrice(getLatestItemPrice(item));
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public List<Item> getNextToFinishItems(String name, String category) {
    if (!name.isEmpty() && !category.isEmpty()) {
      return itemRepository
          .findNextToFinishItemsByNameAndCategory(name, category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));

    } else if (!category.isEmpty() && name.isEmpty()) {
      return itemRepository
          .findNextToFinishItemsByCategory(category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));

    } else if (!name.isEmpty() && category.isEmpty()) {
      return itemRepository
          .findNextToFinishItemsByName(name)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));

    } else {
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
              item.setStatus("EXPIRADO");
              itemRepository.save(item);
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public void sendWinnerNotification() {
    itemRepository
        .findInactiveItems()
        .stream()
        .map(
            (item) -> {
              Subasta subasta = getLatestItem(item);
              if (subasta != null) {
                item.setStatus("VENDIDO");
                User user = subasta.getUser();
                try {
                  sendMail(user, subasta.getItem());
                  logger.info(
                      "El producto se ha vendido, se ha notificado a -> " + user.getEmail());
                } catch (Exception e) {
                  logger.error(
                      "No se pudo enviar el correo de notificación al ganador " + user.getEmail(),
                      e);
                }
              } else {
                item.setStatus("PAUSADO");
                logger.info("La subasta se ha pausado, no se recibió ninguna Puja");
              }
              itemRepository.save(item);
              return item;
            })
        .collect(Collectors.toCollection(ArrayList::new));
    ;
  }

  public List<Item> getBestRatedItems(String name, String category) {
    if (!name.isEmpty() && !category.isEmpty()) {
      return itemRepository
          .findBestRatedItemsByNameAndCategory(name, category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else if (!category.isEmpty() && name.isEmpty()) {
      return itemRepository
          .findBestRatedItemsByCategory(category)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else if (!name.isEmpty() && category.isEmpty()) {
      return itemRepository
          .findBestRatedItemsByName(name)
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    } else {
      return itemRepository
          .findBestRatedItems()
          .stream()
          .map(
              (item) -> {
                byte[] image = LoadDataComponent.decompressBytes(item.getPicture());
                item.setPictureBase64(DatatypeConverter.printBase64Binary(image));
                item.setLatestPrice(getLatestItemPrice(item));
                item.setRanking(rankingRepository.getAvgRanking(item.getId()));
                return item;
              })
          .collect(Collectors.toCollection(ArrayList::new));
    }
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
              item.setRanking(rankingRepository.getAvgRanking(item.getId()));
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

  public Subasta getLatestItem(Item item) {
    List<Subasta> subastas = subastaRepository.findLatestPrice(item.getId());
    if (!subastas.isEmpty()) {
      return subastas.get(0);
    } else {
      return null;
    }
  }

  private void sendMail(User user, Item item)
      throws MessagingException, IOException, TemplateException {
    Map<String, Object> model = new HashMap<>();
    model.put("name", user.getName());
    model.put("product", item.getName());
    model.put("price", item.getLatestPrice());

    EmailTemplate emailTemplate =
        new EmailTemplate(
            user.getEmail(),
            env.getProperty("mail.subasta.subject"),
            env.getProperty("mail.subasta.template"),
            model);

    emailService.sendMessage(emailTemplate);
  }
}
