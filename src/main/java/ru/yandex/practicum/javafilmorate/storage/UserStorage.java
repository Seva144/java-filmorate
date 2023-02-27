package ru.yandex.practicum.javafilmorate.storage;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User createUser(User user) throws ValidationException;

    User updateUser(User user);

    User getUser(int id);

    void addFriend(int id, int idFriend);

    void deleteFriend(int id, int idFriend);

    List<User> getFriends(int id);

}
