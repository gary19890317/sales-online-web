package com.sales.online.model;

public class ItemData {
  private long id;
  private String name;
  private byte[] picture;
  private float startingPrice;
  private float latestPrice;
  private String description;
  private String expirationDate;
  private String status;
  private int ranking;
  private String pictureBase64;

  public ItemData(
      long id,
      String name,
      byte[] picture,
      float startingPrice,
      float latestPrice,
      String description,
      String expirationDate,
      String status,
      int ranking,
      String pictureBase64) {
    super();
    this.id = id;
    this.name = name;
    this.picture = picture;
    this.startingPrice = startingPrice;
    this.latestPrice = latestPrice;
    this.description = description;
    this.expirationDate = expirationDate;
    this.status = status;
    this.ranking = ranking;
    this.pictureBase64 = pictureBase64;
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

  public float getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(float latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public int getRanking() {
    return ranking;
  }

  public void setRanking(int ranking) {
    this.ranking = ranking;
  }

  public String getPictureBase64() {
    return pictureBase64;
  }

  public void setPictureBase64(String pictureBase64) {
    this.pictureBase64 = pictureBase64;
  }
}
