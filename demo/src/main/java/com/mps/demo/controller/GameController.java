package com.mps.demo.controller;

import com.mps.demo.model.User;
import com.mps.demo.service.GameService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    @Autowired
    GameService gameService;

    @PostMapping("/start/{roomName}")
    public ResponseEntity play(@PathVariable String roomName, @RequestHeader("Authorization") String jwt) {
        return gameService.play(roomName, jwt);
    }

    @PostMapping("/play/{roomName}")
    public ResponseEntity<String> startRound(@PathVariable String roomName) {
        return gameService.startRound(roomName);
    }

    @PostMapping("/end/{roomName}")
    public ResponseEntity<Optional<User>> end(@PathVariable String roomName, @RequestHeader("Authorization") String jwt) {
        return gameService.end(roomName,jwt);
    }

    @GetMapping("/score/{roomName}")
    public ResponseEntity getPublic(@PathVariable String roomName) {
        return gameService.getScore(roomName);
    }

    @GetMapping("/descriptionword/{roomName}")
    public ResponseEntity<String> getDescription(@PathVariable String roomName) {
        return gameService.getDescription(roomName);
    }

    @PostMapping("/solve/{roomName}/word/{word}")
    public ResponseEntity<Boolean> solve(@PathVariable String roomName, @PathVariable String word, @RequestHeader("Authorization") String jwt) {
        return gameService.solve(roomName,jwt,word);
    }
}
