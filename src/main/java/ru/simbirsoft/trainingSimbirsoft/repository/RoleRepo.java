package ru.simbirsoft.trainingSimbirsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.trainingSimbirsoft.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
