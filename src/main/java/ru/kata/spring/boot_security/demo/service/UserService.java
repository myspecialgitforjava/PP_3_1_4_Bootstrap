package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Component
public interface UserService {
    List<User> getAllUserList();

    User findByUserName(String username);

    void addUser(User user);

    User getUserById(Long id);

    void update(Long id, User user);

    void delete(Long id);


}
