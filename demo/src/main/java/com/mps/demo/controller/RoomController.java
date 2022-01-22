package com.mps.demo.controller;

import com.mps.demo.model.Room;
import com.mps.demo.service.RoomService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @GetMapping("/{roomName}")
  public ResponseEntity<Optional<Room>> getRoom(@PathVariable String roomName) {
    return ResponseEntity.ok(roomService.getRoom(roomName));
  }

  @PostMapping("/public/enter/{roomName}")
  public ResponseEntity enterPublicRoom(@PathVariable String roomName,
                                        @RequestHeader("Authorization") String jwt) {
    return roomService.enterPublicRoom(roomName, jwt);
  }

  @PostMapping("/private/enter/{roomName}/pass/{password}")
  public ResponseEntity enterPrivateRoom(@PathVariable String roomName,
                                         @RequestHeader("Authorization") String jwt,
                                         @PathVariable String password) {
    return roomService.enterPrivateRoom(roomName, jwt, password);
  }

  @DeleteMapping("/admin/leave/{roomName}")
  public ResponseEntity adminLeaveRoom(@PathVariable String roomName,
                                       @RequestHeader("Authorization") String jwt) {
    return roomService.adminLeaveRoom(roomName, jwt);
  }

  @DeleteMapping("/user/leave/{roomName}")
  public ResponseEntity userLeaveRoom(@PathVariable String roomName,
                                      @RequestHeader("Authorization") String jwt) {
    return roomService.userLeaveRoom(roomName, jwt);
  }

}
