package ru.simbirsoft.trainingSimbirsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.trainingSimbirsoft.domain.Room;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
