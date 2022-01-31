package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Connection;
import ru.simbirsoft.training.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteMessagesByConnection(Connection connection);
    List<Message> findMessagesByConnection(Connection connection);
}
