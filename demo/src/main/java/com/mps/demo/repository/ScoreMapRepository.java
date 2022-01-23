package com.mps.demo.repository;

import com.mps.demo.model.Game;
import com.mps.demo.model.ScoreMap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreMapRepository extends JpaRepository<ScoreMap, Long> {

  List<ScoreMap> getScoreMapByGameIdOrderByScoreDesc(Long id);

  ScoreMap getScoreMapByGameIdAndUserName(Long id, String userName);
}
