package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Показаны все юзеры");
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Пользователь" + user.getName() + " добавлен");
        return userService.createUser(user);
    }

    @PutMapping
    public User changeUser(@Valid @RequestBody User user) throws Exception {
        log.info("Пользователь" + user.getName() + " удален");
        return userService.changeUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) throws NotFoundException {
        log.info("Пользователь показан");
        return userService.getUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@RequestBody @PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) throws ValidationException, NotFoundException {
        log.info("Добавление в друзья");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) throws ValidationException, NotFoundException {
        log.info("Удаление из друзей");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") Integer id) throws NotFoundException {
        log.info("Показаны друзья пользователя");
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) throws NotFoundException {
        return userService.commonFriends(id, otherId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        return new ErrorResponse(
                "Ошибка c параметром id", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Exception e){
        return new ErrorResponse(
                "Непредвиденная ошибка", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFound(final NotFoundException e){
        return new ErrorResponse(
                "Такой пользователь не найден", e.getMessage()
        );
    }


}

