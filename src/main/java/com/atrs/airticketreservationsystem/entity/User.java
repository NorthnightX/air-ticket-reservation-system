package com.atrs.airticketreservationsystem.entity;


public class User {

  private Long id;
  private String username;
  private String account;
  private String password;
  private String avatar;
  private String phoneNumber;
  private String email;
  private String idNumber;
  private Long age;
  private String gender;
  private Long vipStatus;
  private String accountStatus;
  private Double totalExpenses;
  private String userType;


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


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }


  public long getVipStatus() {
    return vipStatus;
  }

  public void setVipStatus(long vipStatus) {
    this.vipStatus = vipStatus;
  }


  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }


  public double getTotalExpenses() {
    return totalExpenses;
  }

  public void setTotalExpenses(double totalExpenses) {
    this.totalExpenses = totalExpenses;
  }


  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

}
