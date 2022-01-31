package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.domain.Room;
import ru.simbirsoft.training.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Optional<Connection> findByUserAndRoom(User user, Room room);

    List<Connection> findConnectionsByUser(User user);
    List<Connection> findConnectionsByRoom(Room room);

    void deleteConnectionsByUser(User user);
    void deleteConnectionsByRoom(Room room);
}
