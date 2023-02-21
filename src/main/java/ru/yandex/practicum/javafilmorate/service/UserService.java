package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public UserService(@Qualifier(value = "userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) throws ValidationException {
        validateUser(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws NotFoundException {
        if (getUser(user.getId()) != null) {
            return userStorage.updateUser(user);
        } else {
            throw new NotFoundException();
        }
    }

    public User getUser(int id) throws NotFoundException {
        if (userStorage.getUser(id) != null) {
            return userStorage.getUser(id);
        } else {
            throw new NotFoundException();
        }
    }

    public void addFriend(int id, int idFriend) throws NotFoundException {
        getUser(id);
        getUser(idFriend);
        userStorage.addFriend(id, idFriend);
    }

    public void deleteFriend(int id, int idFriend) throws NotFoundException {
        getUser(id);
        getUser(idFriend);
        userStorage.deleteFriend(id, idFriend);
    }

    public List<User> getFriends(int id) {
        return userStorage.getFriends(id);
    }

    public List<User> commonFriends(int idUser, int idFriend) {
        List<User> user1 = getFriends(idUser);
        List<User> user2 = getFriends(idFriend);
        return user1.stream().filter(user2::contains)
                .collect(Collectors.toList());
    }

    public void validateUser(User user) {
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }

}
