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

@Component
public class LoadDataComponent {

  private ItemRepository itemRepository;

  public LoadDataComponent(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
    loadData();
  }

  private void loadData() {
    // TODO carga de imagen al server
    // Revisar:  https://dzone.com/articles/upload-and-retrieve-filesimages-using-spring-boot
    saveItem("Control Nintengo64", "/load/n64.jpg", 125, "2020-12-10T15:35:00", "ACTIVE", 0);
    saveItem("Play Station 4", "/load/ps4.jpg", 347, "2020-12-10T18:21:00", "ACTIVE", 0);
    saveItem("Super Sness", "/load/sness.jpg", 289, "2020-12-10T21:19:00", "ACTIVE", 0);
    saveItem("Wii", "/load/wii.jpg", 321, "2020-12-10T10:00:00", "ACTIVE", 0);

    saveItem("Play Statio 5", "/load/ps5.jpg", 460, "2020-12-09T23:59:00", "ACTIVE", 0);
    saveItem("Super Nintendo", "/load/game-cube.jpg", 160, "2020-12-09T22:10:59", "ACTIVE", 0);
    saveItem("PC Gamer", "/load/pc.jpg", 987, "2020-12-09T23:04:00", "ACTIVE", 0);

    saveItem("Play Station 2", "/load/ps2.jpg", 567, "2020-12-15T14:43:00", "ACTIVE", 5);
    saveItem("Wii U", "/load/wii-u.jpg", 289, "2020-12-15T15:39:00", "ACTIVE", 3);
  }

  private void saveItem(
      String name, String imagePath, float price, String date, String status, int ranking) {
    try {
      byte[] image =
          Files.readAllBytes(
              new File(LoadDataComponent.class.getResource(imagePath).toURI()).toPath());
      Item item =
          new Item(
              name,
              compressBytes(image),
              price,
              Timestamp.valueOf(LocalDateTime.parse(date)),
              status,
              ranking);
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
