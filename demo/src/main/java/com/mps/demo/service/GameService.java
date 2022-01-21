package com.mps.demo.service;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import com.mps.demo.repository.RoomRepository;
import com.mps.demo.repository.UserRepository;
import com.mps.demo.service.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    public static String StartRound(Room room){
       return room.getGame().getWordsToGuess().get("iepuras");
    }

    public static Optional<User> end(Room room){
        String name  = room.getGame().getScore().entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        UserRepository userRepository =  null;
        for (Map.Entry<String,Integer> entry : room.getGame().getScore().entrySet()){
            userRepository.findByName(entry.getKey()).get().addscore(1000);
        }
            Optional<User> user = userRepository.findByName(name);
            user.get().addscore(2000);
        return user;
    }

    public static boolean solve(Room room,String jwt,String word){
        JwtUtils jwtUtils = new JwtUtils();
        UserRepository userRepository =  null;
        Optional<User> user = userRepository.findByName(jwtUtils.getUserNameFromJwtToken(jwt));
        if(room.getGame().getWordsToGuess().get("iepuras") == word){
            room.getGame().getScore().put(user.get().getName(),room.getGame().getScore().get(user.get().getName())+1000);
            return true;
        }
        return false;
    }
}
