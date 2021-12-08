package ru.simbirsoft.trainingSimbirsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.trainingSimbirsoft.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
