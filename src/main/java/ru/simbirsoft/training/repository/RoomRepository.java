package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
