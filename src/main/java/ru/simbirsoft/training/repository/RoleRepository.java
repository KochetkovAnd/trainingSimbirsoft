package ru.simbirsoft.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.training.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
