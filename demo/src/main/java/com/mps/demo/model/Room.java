package com.mps.demo.model;

import com.sun.istack.NotNull;
import java.util.List;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;



  @Column(unique=true)
  @NotNull
  private String name;

  @NotNull
  private String roomType;

  private String password;

  private Integer maxPlayerNum;

  private String gameName;

  private boolean GameStarted;

  @Transient
  private Game game;

  public Game getGame() {
    return game;
  }

  public void setGameStarted(boolean gameStarted) {
    GameStarted = gameStarted;
  }

  @NotNull
  private String adminName;

  @ManyToMany
  @JoinTable(
      name = "room_users",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "room_id"))
  private List<User> players;

}
