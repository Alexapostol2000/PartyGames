package com.mps.demo.controller;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import com.mps.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    GameService gameService;

    @PostMapping("/game/start/{roomName}")
    public ResponseEntity play(@PathVariable String roomName, @RequestHeader("Authorization") String jwt) {
        return gameService.play(roomName, jwt);
    }

    @PostMapping
    public ResponseEntity<String> startRound(@RequestBody Room room) {
        return ResponseEntity.ok(gameService.startRound(room));
    }

    @PostMapping
    public ResponseEntity<Optional<User>> end(@RequestBody Room room) {
        return ResponseEntity.ok(gameService.end(room));
    }

    @GetMapping("/score/{roomName}")
    public ResponseEntity getPublic(@PathVariable String roomName) {
        return gameService.getScore(roomName);
    }

    @PostMapping
    public ResponseEntity<Boolean> end(@RequestBody Room room, @RequestParam String word, @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(gameService.solve(room,jwt,word));
    }
}
