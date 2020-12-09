package com.sales.online.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  @Column(length = 1000000)
  private byte[] picture;

  private float startingPrice;
  private String expirationDate;
  private String status;
  @Transient private boolean active;
  @Transient private String pictureBase64;

  public Item() {}

  public Item(
      String name, byte[] picture, float startingPrice, String expirationDate, String status) {
    super();
    this.name = name;
    this.picture = picture;
    this.startingPrice = startingPrice;
    this.expirationDate = expirationDate;
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getPicture() {
    return picture;
  }

  public void setPicture(byte[] picture) {
    this.picture = picture;
  }

  public float getStartingPrice() {
    return startingPrice;
  }

  public void setStartingPrice(float startingPrice) {
    this.startingPrice = startingPrice;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isActive() {
    return !"SOLD".equals(status);
  }

  public String getPictureBase64() {
    return pictureBase64;
  }

  public void setPictureBase64(String pictureBase64) {
    this.pictureBase64 = "data:image/jpeg;base64," + pictureBase64;
  }

  @Override
  public String toString() {
    return "Item [id="
        + id
        + ", name="
        + name
        + ", startingPrice="
        + startingPrice
        + ", expirationDate="
        + expirationDate
        + ", status="
        + status
        + ", active="
        + active
        + "]";
  }
}
