package com.mps.demo.service;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import com.mps.demo.repository.RoomRepository;
import com.mps.demo.service.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameService {
    @Autowired
    RoomRepository gameRepository;

    @Autowired
    JwtUtils jwtUtils;

    public static Game play(Room room, String jwt){
        Game game = room.getGame();
        room.setGameStarted(true);
        game.getScore().clear();
        for (User user : room.getPlayers()){
            game.getScore().put(user.getName(),0);
        }
    return game;
    }
}
