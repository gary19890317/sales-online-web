package com.sales.online.model;

public class PujarData {
  private int itemId;
  private int userId;
  private float puja;
  private float latestPrice;
  private String error;

  public PujarData(int itemId, int userId, float puja, float latestPrice) {
    super();
    this.itemId = itemId;
    this.userId = userId;
    this.puja = puja;
    this.latestPrice = latestPrice;
  }

  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public float getPuja() {
    return puja;
  }

  public void setPuja(float puja) {
    this.puja = puja;
  }

  public float getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(float latestPrice) {
    this.latestPrice = latestPrice;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return "PujarData [itemId="
        + itemId
        + ", userId="
        + userId
        + ", puja="
        + puja
        + ", latestPrice="
        + latestPrice
        + ", error="
        + error
        + "]";
  }
}
