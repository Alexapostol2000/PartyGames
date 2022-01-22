package com.mps.demo.service;

import com.mps.demo.model.Room;
import com.mps.demo.model.RoomType;
import com.mps.demo.model.User;
import com.mps.demo.repository.RoomRepository;
import com.mps.demo.repository.UserRepository;
import com.mps.demo.service.jwt.JwtUtils;
import java.util.List;
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
  JwtUtils jwtUtils;

  public Room create(Room room, String jwt) {
    room.setAdminName(jwtUtils.getUserNameFromJwtToken(jwt));
    return roomRepository.save(room);
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
    return roomRepository.findAllByRoomType(RoomType.PUBLIC_ROOM.value).stream().filter(room -> !room.isGameStarted())
        .collect(Collectors.toList());
  }

  public Optional<Room> getRoom(String roomName) {
    return roomRepository.findByName(roomName);
  }

  public ResponseEntity enterPublicRoom(String roomName, String jwt) {
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

    room.getPlayers().add(user);
    room.setMaxPlayerNum(room.getMaxPlayerNum() - 1);
    room.getGame().getScore().put(userNameFromJwtToken, 0);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
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

    User user = optionalUser.get();

    room.getPlayers().add(user);
    room.setMaxPlayerNum(room.getMaxPlayerNum() - 1);
    room.getGame().getScore().put(userNameFromJwtToken, 0);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
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

    room.setMaxPlayerNum(room.getMaxPlayerNum() + 1);
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

    List<User> players = room.getPlayers();
    players.remove(user);
    room.setMaxPlayerNum(room.getMaxPlayerNum() + 1);
    roomRepository.save(room);
    return ResponseEntity.ok(room);
  }
}
