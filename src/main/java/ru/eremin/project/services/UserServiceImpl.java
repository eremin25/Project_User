package ru.eremin.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eremin.project.models.Role;
import ru.eremin.project.models.User;
import ru.eremin.project.repositories.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /*
    Получение пользователя из базы данных по Login.
    */
    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }


    /*
    Получение всех существующих пользователей из базы данных.
    */
    @Override
    public Set<User> findAllUsers() {
        return userRepository.findAll().stream().collect(Collectors.toSet());
    }


    /*
    Добавление нового пользователя в базу данных, если пользователь с таким логином уже существует
    выбрасывает исключение.
    */
    @Override
    public void saveUser(User user) {
        if (userRepository.findUserByLogin(user.getLogin()) == null) {
            userRepository.save(user);
        } else {
            throw new RuntimeException("Пользователь уже существует");
        }
    }

    /*
    Удаление пользователя по ID.
    */
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    /*
    Изменение пользователя в базе данных
    */
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }


    /*
    Получение пользователя по ID.
    */
    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", login));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
