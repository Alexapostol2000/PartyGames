package com.mps.demo.controller;

import com.mps.demo.model.Room;
import com.mps.demo.service.RoomService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

  @Autowired
  RoomService roomService;

  @PostMapping
  public ResponseEntity<Room> create(@RequestBody Room room, @RequestHeader("Authorization") String jwt) {
    return ResponseEntity.ok(roomService.create(room, jwt));
  }

  @PutMapping
  public ResponseEntity<Optional<Room>> update(@RequestBody Room room, @RequestHeader("Authorization") String jwt) {
    return ResponseEntity.ok(roomService.update(room, jwt));
  }

  @GetMapping("/public")
  public ResponseEntity<List<Room>> getPublic() {
    return ResponseEntity.ok(roomService.getPublicRooms());
  }

  @GetMapping("/private")
  public ResponseEntity<List<Room>> getPrivate() {
    return ResponseEntity.ok(roomService.getPrivateRooms());
  }

  @GetMapping("/{roomName}")
  public ResponseEntity<Optional<Room>> getRoom(@PathVariable String roomName) {
    return ResponseEntity.ok(roomService.getRoom(roomName));
  }
  
}
