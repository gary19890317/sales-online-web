package com.sales.online.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
  private float latestPrice;
  private Timestamp created;
  private Timestamp expirationDate;
  
  @Transient private String expirationDate_aux;
  private String status;
  private int ranking;
  @Transient private boolean active;
  @Transient private String pictureBase64;

  public Item() {}

  public Item(
		  String name, String expirationDate_aux, float startingPrice, float latestPrice,
		  String picture, int ranking, String status
		  ) {
	  this.name=name;
	  this.expirationDate_aux=expirationDate_aux;
	  this.startingPrice=startingPrice;
	  this.latestPrice=latestPrice;
	  this.picture=this.picture;
	  this.ranking=ranking;
	  this.status=status; 
  }
  public Item(
      String name, byte[] picture, float startingPrice, Timestamp expirationDate, String status) {
    super();
    this.name = name;
    this.picture = picture;
    this.startingPrice = startingPrice;
    this.latestPrice = startingPrice;
    this.expirationDate = expirationDate;
    this.status = status;
    this.created = Timestamp.valueOf(LocalDateTime.now());
  }

  public Item(
      String name,
      byte[] picture,
      float startingPrice,
      Timestamp expirationDate,
      String status,
      int ranking) {
    super();
    this.name = name;
    this.picture = picture;
    this.startingPrice = startingPrice;
    this.latestPrice = startingPrice;
    this.expirationDate = expirationDate;
    this.status = status;
    this.ranking = ranking;
    this.created = Timestamp.valueOf(LocalDateTime.now());
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

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public Timestamp getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Timestamp expirationDate) {
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

  public boolean isActive() {
    return !"SOLD".equals(status);
  }

  public String getPictureBase64() {
    return pictureBase64;
  }

  public void setPictureBase64(String pictureBase64) {
    this.pictureBase64 = "data:image/jpeg;base64," + pictureBase64;
  }

  public String getExpirationDate_aux() {
	return expirationDate_aux;
  }

  public void setExpirationDate_aux(String expirationDate_aux) {
	this.expirationDate_aux = expirationDate_aux;
  }

  @Override
  public String toString() {
    return "Item [id="
        + id
        + ", name="
        + name
        + ", startingPrice="
        + startingPrice
        + ", latestPrice="
        + latestPrice
        + ", expirationDate="
        + expirationDate
        + ", status="
        + status
        + ", ranking="
        + ranking
        + "]";
  }
}
