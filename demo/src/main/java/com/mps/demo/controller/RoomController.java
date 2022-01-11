package com.mps.demo.controller;

import com.mps.demo.model.Room;
import com.mps.demo.service.RoomService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController {

  @Autowired
  RoomService roomService;

  @PostMapping
  public ResponseEntity<Room> create(@RequestBody Room room, @RequestHeader("Authorization") String userName) {
    return ResponseEntity.ok(roomService.create(room, userName));
  }

  @PutMapping
  public ResponseEntity<Optional<Room>> update(@RequestBody Room room, @RequestHeader("Authorization") String userName) {
    return ResponseEntity.ok(roomService.update(room, userName));
  }

  @GetMapping("/public")
  public ResponseEntity<List<Room>> getPublic() {
    return ResponseEntity.ok(roomService.getPublicRooms());
  }

  @GetMapping("/{roomName}")
  public ResponseEntity<Optional<Room>> getRoom(@PathVariable String roomName) {
    return ResponseEntity.ok(roomService.getRoom(roomName));
  }
  
}
