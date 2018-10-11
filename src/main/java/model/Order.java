package model;

import java.util.ArrayList;

public class Order {

  private int id;
  private User customer;
  private ArrayList<LineItem> lineItems;
  private Address billingAddress;
  private Address shippingAddress;
  private float orderTotal;
  private long createdAt;
  private long updatedAt;

  public Order() {}

  public Order(
      User customer,
      ArrayList<LineItem> lineItems,
      Address billingAddress,
      Address shippingAddress,
      float orderTotal,
      long createdAt,
      long updatedAt) {
    this.customer = customer;
    this.lineItems = lineItems;
    this.billingAddress = billingAddress;
    this.shippingAddress = shippingAddress;
    this.orderTotal = orderTotal;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Order(
      int id,
      User customer,
      ArrayList<LineItem> lineItems,
      Address billingAddress,
      Address shippingAddress,
      float orderTotal,
      long createdAt,
      long updatedAt) {
    this.id = id;
    this.customer = customer;
    this.lineItems = lineItems;
    this.billingAddress = billingAddress;
    this.shippingAddress = shippingAddress;
    this.orderTotal = orderTotal;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getCustomer() {
    return customer;
  }

  public void setCustomer(User customer) {
    this.customer = customer;
  }

  public ArrayList<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(ArrayList<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public float getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(float orderTotal) {
    this.orderTotal = orderTotal;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(long updatedAt) {
    this.updatedAt = updatedAt;
  }

  // Calculate the order total
  public float calculateOrderTotal(){

    float total = 0;

    //Loop through each item to calculate the total
    for(LineItem item : this.getLineItems()){

      total += item.getPrice();
    }

    this.setOrderTotal(total);
    return total;
  }
}
