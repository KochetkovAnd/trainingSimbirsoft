package ru.simbirsoft.trainingSimbirsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.trainingSimbirsoft.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
