package ru.yandex.practicum.javafilmorate.storage;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User createUser(User user) throws ValidationException;

    User changeUser(User user) throws Exception;

    User getUser(int id) throws NotFoundException;

}
