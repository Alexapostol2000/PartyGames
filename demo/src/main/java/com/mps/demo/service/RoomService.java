package com.mps.demo.service;

import com.mps.demo.model.Room;
import com.mps.demo.model.RoomType;
import com.mps.demo.repository.RoomRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoomService {

  @Autowired
  RoomRepository roomRepository;

  public Room create(Room room, String userName) {
    room.setAdminName(userName);
    return roomRepository.save(room);
  }

  public Optional<Room> update(Room roomRequest, String userName) {
    Optional<Room> roomOptional = roomRepository.findByName(roomRequest.getName());
    if (roomOptional.isEmpty()) {
      log.debug("The room {} could not be found", roomRequest.getName());
      return Optional.empty();
    }
    Room room = roomOptional.get();
    if (!room.getAdminName().equals(userName)) {
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
}
