package ru.practicum.service.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
