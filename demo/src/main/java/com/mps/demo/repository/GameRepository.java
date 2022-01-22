package com.mps.demo.repository;

import com.mps.demo.model.Game;
import com.mps.demo.model.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
