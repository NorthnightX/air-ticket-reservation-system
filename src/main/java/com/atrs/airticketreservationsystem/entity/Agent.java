package com.atrs.airticketreservationsystem.entity;


public class Agent {

  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String qualificationNumber;
  private String level;
  private String status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getQualificationNumber() {
    return qualificationNumber;
  }

  public void setQualificationNumber(String qualificationNumber) {
    this.qualificationNumber = qualificationNumber;
  }


  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
