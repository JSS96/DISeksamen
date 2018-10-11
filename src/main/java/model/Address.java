package model;

public class Address {

  private int id;
  private String name;
  private String streetAddress;
  private String city;
  private String zipCode;

  public Address(){}

  public Address(int id, String name, String streetAddress, String city, String zipCode){
    this.id = id;
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.zipCode = zipCode;
  }

  public Address(String name, String streetAddress, String city, String zipCode){
    this.name = name;
    this.streetAddress = streetAddress;
    this.city = city;
    this.zipCode = zipCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
