package com.sales.online.model;

public class CarItem {
  private String name;
  private String pictureBase64;
  private float offer;

  public CarItem(String name, String pictureBase64, float offer) {
    super();
    this.name = name;
    setPictureBase64(pictureBase64);
    this.offer = offer;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPictureBase64() {
    return pictureBase64;
  }

  public void setPictureBase64(String pictureBase64) {
    this.pictureBase64 = "data:image/jpeg;base64," + pictureBase64;
  }

  public float getOffer() {
    return offer;
  }

  public void setOffer(float offer) {
    this.offer = offer;
  }
}
