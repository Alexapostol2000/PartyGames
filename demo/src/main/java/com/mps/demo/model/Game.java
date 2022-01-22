package com.mps.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Game {

    @Id
    private Long id;
    @Transient
    private Map<String, String> wordsToGuess = new HashMap<>();
    @Transient
    private Map<String, Integer> score = new HashMap<>();

    public Map<String, String> getWordsToGuess() {
        this.wordsToGuess.put("albina","Se plimba din floare in floare si culege ceva dulce.");
        this.wordsToGuess.put("masina","Ne deplasam cu ea pe strada.");
        return wordsToGuess;
    }


}
