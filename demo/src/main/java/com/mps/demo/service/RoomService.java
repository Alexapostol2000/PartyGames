package com.mps.demo.service;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.model.RoomType;
import com.mps.demo.model.ScoreMap;
import com.mps.demo.model.User;
import com.mps.demo.repository.GameRepository;
import com.mps.demo.repository.RoomRepository;
import com.mps.demo.repository.ScoreMapRepository;
import com.mps.demo.repository.UserRepository;
import com.mps.demo.service.jwt.JwtUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomService {

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  GameRepository gameRepository;

  @Autowired
  ScoreMapRepository scoreMapRepository;

  @Autowired
  JwtUtils jwtUtils;

  public Room create(Room room, String jwt) {
    room.setAdminName(jwtUtils.getUserNameFromJwtToken(jwt));
    room.setGameStarted(false);
    room.setCurrentPlayerNum(0);
    Room r = roomRepository.save(room);
    Game game = new Game();
    game.setId(r.getId());
    gameRepository.save(game);
    r.setGame(game);
    return roomRepository.save(r);
  }

  public Optional<Room> update(Room roomRequest, String jwt) {
    Optional<Room> roomOptional = roomRepository.findByName(roomRequest.getName());
    if (!roomOptional.isPresent()) {
      log.debug("The room {} could not be found", roomRequest.getName());
      return Optional.empty();
    }
    Room room = roomOptional.get();
    if (!room.getAdminName().equals(jwtUtils.getUserNameFromJwtToken(jwt))) {
      log.debug("The room {} could not be updated, user is not admin", roomRequest.getName());
      return Optional.empty();
    }
    roomRequest.setId(room.getId());
    return Optional.of(roomRepository.save(room));
  }

  public List<Room> getPublicRooms() {
    return roomRepository.findAllByRoomType(RoomType.PUBLIC_ROOM.value);
  }

  public Optional<Room> getRoom(String roomName) {
    return roomRepository.findByName(roomName);
  }

  public ResponseEntity enterPublicRoom(String roomName, String jwt) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (roomExists(roomName, optionalRoom)) {
      return ResponseEntity.badRequest().body("The room with " + roomName + " is missing");
    }

    String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(jwt);
    Optional<User> optionalUser = userRepository.findByName(userNameFromJwtToken);
    if (!optionalUser.isPresent()) {
      log.debug("The player with username {} is missing", userNameFromJwtToken);
      return ResponseEntity.badRequest().body("The player with username  " + userNameFromJwtToken + "is missing");
    }

    Room room = optionalRoom.get();
    User user = optionalUser.get();

    if (Objects.equals(room.getMaxPlayerNum(), room.getCurrentPlayerNum())) {
      log.debug("The room has reached max capacity " + room.getMaxPlayerNum());
      return ResponseEntity.badRequest().body("The room has reached max capacity " + room.getMaxPlayerNum());

    }

    room.getPlayers().add(user);
    setCurrentPlayerNum(room);
    createNewScoreEntry(room,userNameFromJwtToken);
    return ResponseEntity.ok(room);
  }

  private boolean roomExists(String roomName, Optional<Room> optionalRoom) {
    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return true;
    }
    return false;
  }

  public ResponseEntity enterPrivateRoom(String roomName, String jwt, String password) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return ResponseEntity.badRequest().body("The room with " + roomName + "is missing");
    }

    Room room = optionalRoom.get();
    if (!password.equals(room.getPassword())) {
      log.debug("The password {} is wrong", password);
      return ResponseEntity.badRequest().body("The password " + password + "is wrong");
    }

    String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(jwt);
    Optional<User> optionalUser = userRepository.findByName(userNameFromJwtToken);
    if (!optionalUser.isPresent()) {
      log.debug("The player with username {} is missing", userNameFromJwtToken);
      return ResponseEntity.badRequest().body("The player with username  " + userNameFromJwtToken + "is missing");
    }

    if (room.getMaxPlayerNum().equals(room.getCurrentPlayerNum())) {
      log.debug("The room has reached max capacity " + room.getMaxPlayerNum());
      return ResponseEntity.badRequest().body("The room has reached max capacity " + room.getMaxPlayerNum());

    }

    User user = optionalUser.get();

    room.getPlayers().add(user);
    setCurrentPlayerNum(room);

    createNewScoreEntry(room, userNameFromJwtToken);

    return ResponseEntity.ok(room);
  }

  private void createNewScoreEntry(Room room, String userNameFromJwtToken) {
    Game game = room.getGame();
    ScoreMap newScoreEntry = new ScoreMap();
    newScoreEntry.setUserName(userNameFromJwtToken);
    newScoreEntry.setScore(0);
    newScoreEntry.setGameId(game.getId());
    scoreMapRepository.save(newScoreEntry);
    game.getScore().add(newScoreEntry);
    gameRepository.save(game);
    roomRepository.save(room);
  }

  private void setCurrentPlayerNum(Room room) {
    Integer size = room.getPlayers().size();
    room.setCurrentPlayerNum(size);
  }

  public ResponseEntity adminLeaveRoom(String roomName, String jwt) {
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

    if(!user.getName().equals(room.getAdminName())) {
      log.debug("The user is not the admin of the room");
      return ResponseEntity.badRequest().body("The user" + user.getName() + " is not the admin of the room" + roomName) ;
    }

    List<User> players = room.getPlayers();
    players.remove(user);
    if(!players.isEmpty()) {
      User otherPlayer = players.get(0);
      room.setAdminName(otherPlayer.getName());
    }

    setCurrentPlayerNum(room);
    ScoreMap score = scoreMapRepository.getScoreMapByGameIdAndUserName(room.getGame().getId(),user.getName());
    room.getGame().getScore().remove(score);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
  }

  public ResponseEntity userLeaveRoom(String roomName, String jwt) {
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

    ScoreMap score = scoreMapRepository.getScoreMapByGameIdAndUserName(room.getGame().getId(),user.getName());
    room.getGame().getScore().remove(score);

    List<User> players = room.getPlayers();
    players.remove(user);
    setCurrentPlayerNum(room);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
  }

  public List<Room> getPrivateRooms() {
    return roomRepository.findAllByRoomType(RoomType.PRIVATE_ROOM.value);
  }
}
