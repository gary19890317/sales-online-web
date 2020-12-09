package com.sales.online.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    saveItem("Control Nintengo64", "/load/n64.jpg", 125, "2020-12-08", "ACTIVE");
    saveItem("Play Station 4", "/load/ps4.jpg", 347, "2020-12-08", "ACTIVE");
    saveItem("Super Nintendo", "/load/sness.jpg", 289, "2020-12-08", "ACTIVE");
  }

  private void saveItem(String name, String imagePath, float price, String date, String status) {
    try {
      byte[] image =
          Files.readAllBytes(
              new File(LoadDataComponent.class.getResource(imagePath).toURI()).toPath());
      Item item = new Item(name, compressBytes(image), price, date, status);
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
