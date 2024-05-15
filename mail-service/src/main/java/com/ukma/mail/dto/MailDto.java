package com.ukma.mail.dto;

import java.util.List;

public class MailDto {

  private String receiver;
  private String username;
  private List<EmailBasketItemDto> basketItems;

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<EmailBasketItemDto> getBasketItems() {
    return basketItems;
  }

  public void setBasketItems(List<EmailBasketItemDto> basketItems) {
    this.basketItems = basketItems;
  }
}
