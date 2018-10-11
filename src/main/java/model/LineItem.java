package model;

public class LineItem {

  private int id;
  private Product product;
  private int quantity;
  private float price;

  public LineItem(int id, Product product, int quantity, float price) {
    this.id = id;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public LineItem(Product product, int quantity, float price) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }
}
