package com.sales.online.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  private String category;

  @Column(length = 1000000)
  private byte[] picture;

  private float startingPrice;
  private float latestPrice;
  private String description;
  private Timestamp created;
  private Timestamp expirationDate;

  @Transient private String expirationDate_aux;
  private String status;
  private int ranking;
  @Transient private boolean active;
  @Transient private String pictureBase64;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  private User user;

  public Item() {}

  public Item(
      String name,
      String category,
      String expirationDate_aux,
      float startingPrice,
      float latestPrice,
      String description,
      String picture,
      int ranking,
      String status) {
    this.name = name;
    this.category = category;
    this.expirationDate_aux = expirationDate_aux;
    this.startingPrice = startingPrice;
    this.latestPrice = latestPrice;
    this.ranking = ranking;
    this.status = status;
  }

  public Item(
      String name,
      String category,
      byte[] picture,
      float startingPrice,
      String description,
      Timestamp expirationDate,
      String status) {
    super();
    this.name = name;
    this.category = category;
    this.picture = picture;
    this.startingPrice = startingPrice;
    this.description = description;
    this.latestPrice = startingPrice;
    this.expirationDate = expirationDate;
    this.status = status;
    this.created = Timestamp.valueOf(LocalDateTime.now());
  }

  public Item(
      String name,
      String category,
      byte[] picture,
      String description,
      float startingPrice,
      Timestamp expirationDate,
      String status,
      int ranking) {
    super();
    this.name = name;
    this.category = category;
    this.picture = picture;
    this.description = description;
    this.startingPrice = startingPrice;
    this.latestPrice = startingPrice;
    this.expirationDate = expirationDate;
    this.status = status;
    this.ranking = ranking;
    //Date date_aux = new Date();
    //System.out.println("Time zone del item actual "+ name+" "+ new Timestamp(date_aux.getTime()));
    this.created = Timestamp.valueOf(LocalDateTime.now());//new Timestamp(date_aux.getTime());//
    
    
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
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
    return !"VENDIDO".equals(status);
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
        +" created: "+ created
        + ", status="
        + status
        + ", ranking="
        + ranking
        + "]";
  }
}
