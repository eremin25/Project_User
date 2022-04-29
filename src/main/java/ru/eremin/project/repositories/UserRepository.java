package ru.eremin.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eremin.project.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLogin(String login);
}
