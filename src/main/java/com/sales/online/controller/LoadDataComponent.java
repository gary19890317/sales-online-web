package com.sales.online.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.stereotype.Component;

import com.sales.online.model.Item;
import com.sales.online.repository.ItemRepository;
import com.sales.online.repository.UserRepository;

@Component
public class LoadDataComponent {

  private ItemRepository itemRepository;
  private UserRepository userRepository;

  public LoadDataComponent(ItemRepository itemRepository, UserRepository userRepository) {
    this.itemRepository = itemRepository;
    this.userRepository = userRepository;
    loadData();
  }

  private void loadData() {
    addItem(
        "Control Nintengo64",
        "/load/n64.jpg",
        "Control video juego nintento 64",
        125,
        "2020-12-18T18:29:00",
        "ACTIVO",
        3);
    addItem(
        "Play Station 4",
        "/load/ps4.jpg",
        "Consola de video juego play station 4",
        347,
        "2020-12-18T18:21:00",
        "ACTIVO",
        5);
    addItem(
        "Super Sness",
        "/load/sness.jpg",
        "Conola de video juego clásico Sness",
        289,
        "2020-12-27T21:19:00",
        "ACTIVO",
        3);
    addItem(
        "Wii",
        "/load/wii.jpg",
        "Consola de video juego Wii",
        321,
        "2020-12-10T10:00:00",
        "ACTIVO",
        0);

    addItem(
        "Play Statio 5",
        "/load/ps5.jpg",
        "Consola de video juego play station 4",
        460,
        "2020-12-09T23:59:00",
        "ACTIVO",
        0);
    addItem(
        "Super Nintendo",
        "/load/game-cube.jpg",
        "Consola de video juego clásico Super nintendo",
        160,
        "2020-12-09T22:10:59",
        "ACTIVO",
        0);
    addItem(
        "PC Gamer",
        "/load/pc.jpg",
        "PC Gamer Intel core i7 10th",
        987,
        "2020-12-09T23:04:00",
        "ACTIVO",
        0);

    addItem(
        "Play Station 2",
        "/load/ps2.jpg",
        "Consola de video juego play station 2",
        567,
        "2020-12-15T14:43:00",
        "ACTIVO",
        5);
    addItem(
        "Wii U",
        "/load/wii-u.jpg",
        "Consola de video juego Wii U",
        289,
        "2020-12-18T18:39:00",
        "ACTIVO",
        3);
  }

  private void addItem(
      String name,
      String imagePath,
      String description,
      float price,
      String date,
      String status,
      int ranking) {
    try {
      byte[] image =
          Files.readAllBytes(
              new File(LoadDataComponent.class.getResource(imagePath).toURI()).toPath());
      Item item =
          new Item(
              name,
              compressBytes(image),
              description,
              price,
              Timestamp.valueOf(LocalDateTime.parse(date)),
              status,
              ranking);
      item.setUser(userRepository.findById(1).get());
      itemRepository.save(item);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
    return outputStream.toByteArray();
  }

  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (DataFormatException e) {
      e.printStackTrace();
    }
    return outputStream.toByteArray();
  }
}
