package com.mps.demo.repository;

import com.mps.demo.model.Room;
import com.mps.demo.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
  Optional<Room> findByName(String name);

  List<Room> findAllByRoomType(String value);
}
