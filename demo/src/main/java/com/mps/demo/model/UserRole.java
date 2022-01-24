package com.mps.demo.model;

public enum UserRole {

  ROLE_USER("ROLE_USER"),
  ROLE_GUEST("NeROLE_GUEST"),
  ROLE_SPECTATOR("ROLE_SPECTATOR");

  public final String value;

  private UserRole(String value) {
    this.value = value;
  }
}
