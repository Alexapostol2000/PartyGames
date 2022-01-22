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

    @PostMapping("/game/play/{roomName}")
    public ResponseEntity<String> startRound(@PathVariable String roomName) {
        return gameService.startRound(roomName);
    }

    @PostMapping("/game/end/{roomName}")
    public ResponseEntity<Optional<User>> end(@PathVariable String roomName, @RequestHeader("Authorization") String jwt) {
        return gameService.end(roomName,jwt);
    }

    @GetMapping("/score/{roomName}")
    public ResponseEntity getPublic(@PathVariable String roomName) {
        return gameService.getScore(roomName);
    }

    @PostMapping("/game/solve/{roomName}/word/{word}")
    public ResponseEntity<Boolean> end(@PathVariable String roomName, @PathVariable String word, @RequestHeader("Authorization") String jwt) {
        return gameService.solve(roomName,jwt,word);
    }
}
