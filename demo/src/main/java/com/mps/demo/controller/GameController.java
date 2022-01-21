package com.mps.demo.controller;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.service.GameService;
import com.mps.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    GameService gameService;
    @PostMapping
    public ResponseEntity<Game> play(@RequestBody Room room, @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(GameService.play(room, jwt));
    }

}
