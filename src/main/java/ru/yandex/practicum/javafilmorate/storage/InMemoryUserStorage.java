package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final static HashMap<Integer, User> users = new HashMap<>();
    private static int id;

    public int generateId() {
        return ++id;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User createUser(User user) {
        validateUser(user);
        log.debug("Добавление нового пользователя - " + user.getLogin());
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    public User changeUser(User user) throws Exception {
        validateUser(user);
        if(users.containsKey(user.getId())){
            users.get(user.getId());
            log.debug("Обновление пользователя - " + user.getLogin());
            users.replace(user.getId(), user);
            return user;
        } else {
            throw new Exception();
        }
    }

    public User getUser(int id) throws NotFoundException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException();
        }

    }

    public void validateUser(User user) {
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}
