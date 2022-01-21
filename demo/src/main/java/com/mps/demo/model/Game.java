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
    private Map<String, String> wordsToGuess = new HashMap<String, String>();
    @Transient
    private Map<String, Integer> score = new HashMap<String, Integer>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getWordsToGuess() {
        this.wordsToGuess.put("albina","Se plimba din floare in floare si culege ceva dulce.");
        this.wordsToGuess.put("masina","Ne deplasam cu ea pe strada.");
        return wordsToGuess;
    }

    public void setWordsToGuess(Map<String, String> wordsToGuess) {
        this.wordsToGuess = wordsToGuess;
    }

    public Map<String, Integer> getScore() {
        return score;
    }

    public void setScore(Map<String, Integer> score) {
        this.score = score;
    }


}
