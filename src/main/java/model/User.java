package model;

public class User {

  public int id;
  public String firstname;
  public String lastname;
  public String email;
  private String password;
  private long createdTime;
  private String token;

  public User(int id, String firstname, String lastname, String password, String email) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.email = email;
  }

//  Konstuktør for login
  public User(int id, String firstname, String lastname, String password, String email, long createdTime) {
  this.id = id;
  this.firstname = firstname;
  this.lastname = lastname;
  this.password = password;
  this.email = email;
  this.createdTime=createdTime;
}

  public User(int id, String email, String token) {
    this.id = id;
    this.email = email;
    this.token = token;
  }

  public User(String firstname, String lastname, String email, String password) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
  }
}
