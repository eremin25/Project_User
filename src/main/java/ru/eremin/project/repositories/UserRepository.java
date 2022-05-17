package ru.eremin.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.eremin.project.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join fetch u.roles where u.login = :login")
    User findUserByLogin(String login);

    @Query("select u from User u join fetch u.roles where u.id = :id")
    User findUserById(Long id);

    @Query("from User u join fetch u.roles")
    List<User> findAll();
}
