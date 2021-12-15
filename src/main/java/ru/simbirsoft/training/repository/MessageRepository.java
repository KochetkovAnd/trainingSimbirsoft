package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
