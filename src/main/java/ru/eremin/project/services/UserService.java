package ru.eremin.project.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.eremin.project.models.User;

import java.util.Set;

public interface UserService extends UserDetailsService {

    User findUserByLogin(String login);

    Set<User> findAllUsers();

    void saveUser(User user);
}
