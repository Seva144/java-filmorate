package ru.yandex.practicum.javafilmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.controllers.FilmController;
import ru.yandex.practicum.javafilmorate.controllers.UserController;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;


import javax.validation.Valid;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	UserController userController;
	FilmController filmController;
	FilmService filmService;
	UserService userService;
	InMemoryUserStorage userStorage;
	InMemoryFilmStorage filmStorage;

	User user1;
	User user2;
	User user3;

	Film film1;
	Film film2;
	Film film3;

	@BeforeEach
	public void Server_SetUp(){
		userStorage = new InMemoryUserStorage();
		filmStorage = new InMemoryFilmStorage();

		userService = new UserService(userStorage);
		filmService = new FilmService(filmStorage, userStorage);

		userController = new UserController(userService);
		filmController = new FilmController(filmService);

		user1 = new User("user1@mail.ru", "name1", "login1", LocalDate.of(2000,1,1));
		user2 = new User("user2@mail.ru", "name2", "login2", LocalDate.of(2000,1,1));
		user3 = new User("user3@mail.ru", "", "login3", LocalDate.of(2000,1,1));

		film1 = new Film("Индиана Джонс", "Известный археолог и специалист ..",
				LocalDate.of(1981, 1,1), 180);
		film2 = new Film("Мумия", "Про мумию", LocalDate.of(1999, 1,1), 180);
		film3 = new Film("Star Wars: Episode I", "Мальчику с далекой планеты суждено изменить судьбу галактики.",
				LocalDate.of(2000, 1,1), 180);
	}

	@Test
	public void Add_Like() throws ValidationException, NotFoundException {
		userController.createUser(user1);
		userController.createUser(user2);
		filmController.createFilm(film1);
		filmController.createFilm(film2);

		userController.addFriend(user1.getId(), user2.getId());

	}


}
