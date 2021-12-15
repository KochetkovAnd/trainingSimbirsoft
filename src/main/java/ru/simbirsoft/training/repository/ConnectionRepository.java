package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}
