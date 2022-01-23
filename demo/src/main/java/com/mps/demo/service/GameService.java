package com.mps.demo.service;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
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
import java.util.Random;
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
  ScoreMapRepository scoreMapRepository;

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

  public ResponseEntity startRound(String roomName) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return ResponseEntity.badRequest().body("The room with " + roomName + "is missing");
    }
    Room room = optionalRoom.get();
    Game game = room.getGame();
    Object[] crunchifyKeys = game.getWordsToGuess().keySet().toArray();
    Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
    game.setChosenWord((String) key);
    gameRepository.save(game);
    return ResponseEntity.ok(game.getWordsToGuess().get(key));
  }

  public ResponseEntity end(String roomName, String jwt) {
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

    giveBonusToBiggestScoreFromGame(room);

    ResponseEntity<String> userNameFromJwtToken1 = calculateFinalScoreUsers(userNameFromJwtToken, room);
    if (userNameFromJwtToken1 != null) {
      return userNameFromJwtToken1;
    }

    userRepository.save(user);
    roomRepository.save(room);
    return ResponseEntity.ok(user);
  }

  private void giveBonusToBiggestScoreFromGame(Room room) {
    List<ScoreMap> scoreOrdered = scoreMapRepository.getScoreMapByGameIdOrderByScoreDesc(room.getGame().getId());
    ScoreMap biggestScore = scoreOrdered.get(0);
    biggestScore.setScore(biggestScore.getScore() + 3000);
    scoreMapRepository.save(biggestScore);
  }

  private ResponseEntity<String> calculateFinalScoreUsers(String userNameFromJwtToken, Room room) {
    List<ScoreMap> scores = scoreMapRepository.getScoreMapByGameIdOrderByScoreDesc(room.getGame().getId()) ;
    for (ScoreMap s : scores) {
      Optional<User> eachUser = userRepository.findByName(s.getUserName());
      if (!eachUser.isPresent()) {
        log.debug("The player with username {} is missing", userNameFromJwtToken);
        return ResponseEntity.badRequest().body("The player with username  " + userNameFromJwtToken + "is missing");
      }
      User aux = eachUser.get();
      aux.addscore(s.getScore());
      userRepository.save(aux);
    }
    return null;
  }

  public ResponseEntity solve(String roomName, String jwt, String word) {
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

    Game game = room.getGame();
    if (Objects.equals(game.getChosenWord(), word.toLowerCase())) {
      ScoreMap scoreMap = scoreMapRepository.getScoreMapByGameIdAndUserName(game.getId(), user.getName());
      scoreMap.setScore(scoreMap.getScore() + 1000);
      scoreMapRepository.save(scoreMap);
      return ResponseEntity.ok(true);
    }
    roomRepository.save(room);
    return ResponseEntity.ok(false);
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

  public ResponseEntity getDescription(String roomName) {
    Optional<Room> optionalRoom = roomRepository.findByName(roomName);

    if (!optionalRoom.isPresent()) {
      log.debug("The room with {} is missing", roomName);
      return ResponseEntity.badRequest().body("The room with " + roomName + "is missing");
    }
    Room room = optionalRoom.get();
    return ResponseEntity.ok(room.getGame().getWordsToGuess().get(room.getGame().getChosenWord()));
  }
}
