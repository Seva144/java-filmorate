package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage){
        this.userStorage= userStorage;
    }

    public List<User> getAllUsers(){
        return userStorage.getAllUsers();
    }

    public User createUser(User user) throws ValidationException {
        return userStorage.createUser(user);
    }

    public User changeUser(User user) throws Exception {
        return userStorage.changeUser(user);
    }

    public User getUser(int id) throws NotFoundException {
        return userStorage.getUser(id);
    }

    public void addFriend(int id, int idFriend) throws NotFoundException{
        if(validateId(id, idFriend)){
            getUser(id).getFriend().add(idFriend);
            getUser(idFriend).getFriend().add(id);
            log.info("Пользователь - " + getUser(id) + "добавил в друзья" + getUser(idFriend));
        } else {
            throw new NotFoundException();
        }
    }

    public void deleteFriend(int id, int idFriend) throws NotFoundException {
        if(validateId(id, idFriend)) {
            getUser(id).getFriend().remove(idFriend);
            getUser(idFriend).getFriend().remove(id);
            log.info("Пользователь - " + getUser(id) + "удалил из друзей"
                    + getUser(idFriend));
        } else {
            throw new NotFoundException();
        }
    }

    public List<User> getFriends(int id) throws NotFoundException {
        List<User> friends = new ArrayList<>();
        for (Integer friend : getUser(id).getFriend()) {
            friends.add(getUser(friend));
        }
        return friends;
    }

    public List<User> commonFriends(int idUser, int idFriend) throws NotFoundException {
        Set<Integer> user1 = getUser(idUser).getFriend();
        Set<Integer> user2 = getUser(idFriend).getFriend();
        Set<Integer> common = user1.stream().filter(user2::contains)
                .collect(Collectors.toSet());
        List<User> commonFriends = new ArrayList<>();
        for(Integer id: common){
            commonFriends.add(getUser(id));
        }
        return commonFriends;
    }

    public boolean validateId(int id, int idFriend){
        Optional<User> user1 = getAllUsers().stream().filter(user -> user.getId()==id).findAny();
        Optional<User> user2 = getAllUsers().stream().filter(user -> user.getId()==idFriend).findAny();
        return user1.isPresent()&&user2.isPresent();
    }
}
