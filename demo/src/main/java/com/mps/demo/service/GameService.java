package com.mps.demo.service;

import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import com.mps.demo.repository.GameRepository;
import com.mps.demo.repository.RoomRepository;
import com.mps.demo.repository.UserRepository;
import com.mps.demo.service.jwt.JwtUtils;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameService {

  @Autowired
  GameRepository gameRepository;

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  JwtUtils jwtUtils;

  public ResponseEntity play(String roomName, String jwt) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return ResponseEntity.badRequest().body("The room with " + roomName + "is missing");
    }

    String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(jwt);
    Optional<User> optionalUser = userRepository.findByName(userNameFromJwtToken);
    if (!optionalUser.isPresent()) {
      log.debug("The player with username {} is missing", userNameFromJwtToken);
      return ResponseEntity.badRequest().body("The player with username  " + userNameFromJwtToken + "is missing");
    }

    Room room = optionalRoom.get();
    User user = optionalUser.get();

    if (!user.getName().equals(room.getAdminName())) {
      log.debug("The user is not the admin of the room");
      return ResponseEntity.badRequest().body("The user" + user.getName() + " is not the admin of the room" + roomName);
    }

    room.setGameStarted(true);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
  }

  public String startRound(Room room) {
    return room.getGame().getWordsToGuess().get("iepuras");
  }

  public Optional<User> end(Room room) {
    String name = room.getGame().getScore().entrySet().stream()
        .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    UserRepository userRepository = null;
    for (Map.Entry<String, Integer> entry : room.getGame().getScore().entrySet()) {
      userRepository.findByName(entry.getKey()).get().addscore(1000);
    }
    Optional<User> user = userRepository.findByName(name);
    user.get().addscore(2000);
    return user;
  }

  public boolean solve(Room room, String jwt, String word) {
    JwtUtils jwtUtils = new JwtUtils();
    UserRepository userRepository = null;
    Optional<User> user = userRepository.findByName(jwtUtils.getUserNameFromJwtToken(jwt));
    if (room.getGame().getWordsToGuess().get("iepuras") == word) {
      room.getGame().getScore().put(user.get().getName(), room.getGame().getScore().get(user.get().getName()) + 1000);
      return true;
    }
    return false;
  }

  public ResponseEntity getScore(String roomName) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return ResponseEntity.badRequest().body("The room with " + roomName + "is missing");
    }

    Room room = optionalRoom.get();
    return ResponseEntity.ok(room.getGame().getScore());
  }
}
