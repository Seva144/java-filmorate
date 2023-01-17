package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<Integer, User> users = new HashMap<>();
    protected int id;

    @GetMapping
    public List<User> returnAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        validateUser(user);
        log.debug("Добавление нового пользователя - " + user.getLogin());
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User changeUser(@Valid @RequestBody User user) throws ValidationException {
            validateUser(user);
            if (users.containsKey(user.getId())) {
                log.debug("Обновление пользователя - " + user.getLogin());
                users.replace(user.getId(), user);
            } else {
                throw new ValidationException("Неверный ID");
            }
        return user;
    }

    public void validateUser(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public int generateId() {
        return ++id;
    }
}

