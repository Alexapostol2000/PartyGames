package com.mps.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private Map<String, String> wordsToGuess = new HashMap<>();

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "game_score", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "score_id"))
    private List<ScoreMap> score;

    private String chosenWord;

    public Map<String, String> getWordsToGuess() {
        this.wordsToGuess.put("masina","Ne deplasam cu ea pe strada.");
        this.wordsToGuess.put("capcana", "in ce parte, stanga sau dreapta a unei bancnote de zece lei este portretul lui Nicolae Iorga?");
        this.wordsToGuess.put("casa","loc unde sa locuiesti");
        this.wordsToGuess.put("in pamant", "unde s a sapat prima fantana din romania?");
        this.wordsToGuess.put("albina","Se plimba din floare in floare si culege ceva dulce.");
        this.wordsToGuess.put("patru", "cate batai din palme se adu in timpul intro-ului serialului friends?");
        this.wordsToGuess.put("veverita","se catara in copaci si mananca nuci");
        this.wordsToGuess.put("liliac","soricel cu aripioare, poarta numele de floare");
        return wordsToGuess;
    }


}
