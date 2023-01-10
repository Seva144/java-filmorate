package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidIdException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidLocalDateException;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<Integer, User> users = new HashMap<>();
    protected int id;

    @GetMapping(value = "/users")
    public List<User> returnAllUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        try {
            if (validateUser(user)) {
                log.debug("Добавление нового пользователя - " + user.getLogin());
                user.setId(generateId());
                users.put(user.getId(), user);
            }
        } catch (InvalidNameException | InvalidLocalDateException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @PutMapping(value = "/users")
    public User changeUser(@RequestBody User user) {
        try {
            if(users.containsKey(user.getId())) {
                if (validateUser(user)) {
                    log.debug("Обновление пользователя - " + user.getLogin());
                    users.replace(user.getId(), user);
                }
            } else {
                throw new InvalidIdException("Неверный ID");
            }
        } catch (InvalidNameException | InvalidLocalDateException | InvalidIdException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean validateUser(User user) throws InvalidNameException, InvalidLocalDateException {
        if (user.getEmail() == null) {
            log.debug("Пустой email" + user.getName());
            throw new InvalidNameException("Пустой email");
        } else if (!user.getEmail().contains("@")) {
            log.debug("Некорректный адрес email" + user.getEmail());
            throw new StringIndexOutOfBoundsException("Некорректный адрес email");
        } else if (user.getLogin() == null) {
            log.debug("Пустой login" + user.getName());
            throw new InvalidNameException("Пустой login");
        } else if (user.getLogin().contains(" ")) {
            log.debug("Некорректный login" + user.getLogin());
            throw new StringIndexOutOfBoundsException("Некорректный login");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Некорректная дата рождения" + user.getBirthday());
            throw new InvalidLocalDateException("Некорректная дата рождения");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        return true;
    }

    public int generateId() {
        return ++id;
    }
}

