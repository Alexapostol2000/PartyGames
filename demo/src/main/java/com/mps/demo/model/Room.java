package com.mps.demo.model;

import com.sun.istack.NotNull;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

  private String adminName;

  @ManyToMany
  @JoinTable(
      name = "room_users",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "room_id"))
  private List<User> players;

}
