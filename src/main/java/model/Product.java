package model;

public class Product {

  public int id;
  public String name;
  public String sku;
  public float price;
  private String description;

  public Product(int id, String name, String sku, float price, String description) {
    this.id = id;
    this.name = name;
    this.sku = sku;
    this.price = price;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
