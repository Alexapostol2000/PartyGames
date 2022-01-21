package com.mps.demo.controller;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import com.mps.demo.service.GameService;
import com.mps.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping
    public ResponseEntity<String> StartRound(@RequestBody Room room) {
        return ResponseEntity.ok(GameService.StartRound(room));
    }

    @PostMapping
    public ResponseEntity<Optional<User>> end(@RequestBody Room room) {
        return ResponseEntity.ok(GameService.end(room));
    }

    @GetMapping("/score")
    public ResponseEntity<Map<String,Integer>> getPublic(@RequestBody Room room) {
        return ResponseEntity.ok(room.getGame().getScore());
    }

    @PostMapping
    public ResponseEntity<Boolean> end(@RequestBody Room room, @RequestParam String word, @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(GameService.solve(room,jwt,word));
    }
}
