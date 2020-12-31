package com.sales.online.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ranking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private Timestamp created;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_item")
  private Item item;

  private int stars;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  private User user;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getStars() {
    return stars;
  }

  public void setStars(int stars) {
    this.stars = stars;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
