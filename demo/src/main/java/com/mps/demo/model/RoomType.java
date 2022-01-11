package com.mps.demo.model;

public enum RoomType {

  PRIVATE_ROOM("PRIVATE_ROOM"),
  PUBLIC_ROOM("PUBLIC_ROOM");

  public final String value;

  private RoomType(String value) {
    this.value = value;
  }
}
