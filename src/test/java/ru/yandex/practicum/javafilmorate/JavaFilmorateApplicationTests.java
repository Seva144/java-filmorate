package ru.yandex.practicum.javafilmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidDescriptionException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidDurationException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidLocalDateException;
import ru.yandex.practicum.javafilmorate.controllers.FilmController;
import ru.yandex.practicum.javafilmorate.controllers.UserController;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.naming.InvalidNameException;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	UserController userController;
	FilmController filmController;
	User user1;
	User user2;
	User user3;
	User user4;
	User user5;
	User user6;
	User user7;
	Film film1;
	Film film2;
	Film film3;
	Film film4;
	Film film5;

	@BeforeEach
	public void Server_SetUp(){
		userController = new UserController();
		filmController = new FilmController();

		user1 = new User(null, "name1", "login1", LocalDate.of(2000,1,1));
		user2 = new User("email", "name2", "login2", LocalDate.of(2000,1,1));
		user3 = new User("email@yandex.ru", "name3", null, LocalDate.of(2000,1,1));
		user4 = new User("email@yandex.ru", "name4", "login 4", LocalDate.of(2000,1,1));
		user5 = new User("email@yandex.ru", null, "login5", LocalDate.of(2000,1,1));
		user6 = new User("email@yandex.ru", "name6", "login6", LocalDate.of(2024,1,1));
		user7 = new User("email@yandex.ru", "login7", "name7", LocalDate.of(1995,1,1));

		String description = "На бескрайних просторах египетской пустыни компания сорвиголов разных национальностей рыщет" +
				" в поисках несметных сокровищ фараона, над которыми тяготеет жуткое древнее проклятие." +
				"Рядом с кладом покоится мумия коварного жреца, жестоко казненного за ужасное убийство " +
				"могущественного правителя Египта. Золотоискатели потревожили многовековой покой гробницы," +
				" и мумия встает из могилы, чтобы погрузить мир в царство кошмара...\n";

		film1 = new Film(null, "Известный археолог и специалист по оккультным наукам доктор Джонс получает опасное задание",
				LocalDate.of(1981, 1,1), 180);
		film2 = new Film("Мумия", description, LocalDate.of(1999, 1,1), 180);
		film3 = new Film("Star Wars: Episode I", "Мальчику с далекой планеты суждено изменить судьбу галактики.",
				LocalDate.of(1890, 1,1), 180);
		film4 = new Film("Крепкий орешек", "Полицейский в одиночку освобождает захваченный террористами небоскреб.",
				LocalDate.of(1994, 1,1), -180);

		film5 = new Film("Плохие парни", "Напарники охотятся за бандитами, которые выкрали героин из полицейского участка.",
				LocalDate.of(1995, 1,1),180);
	}

	@Test
	public void User1_Add()  {
		Assertions.assertThrows(InvalidNameException.class, ()->userController.validateUser(user1));
	}

	@Test
	public void User2_Add(){
		Assertions.assertThrows(StringIndexOutOfBoundsException.class,()->userController.validateUser(user2));
	}

	@Test
	public void User3_Add(){
		Assertions.assertThrows(InvalidNameException.class,()->userController.validateUser(user3));
	}

	@Test
	public void User4_Add(){
		Assertions.assertThrows(StringIndexOutOfBoundsException.class,()->userController.validateUser(user4));
	}

	@Test
	public void User6_Add(){
		Assertions.assertThrows(InvalidLocalDateException.class, ()->userController.validateUser(user6));
	}

	@Test
	public void User5_Add() throws InvalidLocalDateException, InvalidNameException {
		Assertions.assertTrue(userController.validateUser(user5));
	}

	@Test
	public void Film1_Add(){
		Assertions.assertThrows(InvalidNameException.class,()->filmController.validateFilm(film1));
	}

	@Test
	public void Film2_Add(){
		Assertions.assertThrows(InvalidDescriptionException.class,()->filmController.validateFilm(film2));
	}

	@Test
	public void Film3_Add(){
		Assertions.assertThrows(InvalidLocalDateException.class,()->filmController.validateFilm(film3));
	}

	@Test
	public void Film4_Add(){
		Assertions.assertThrows(InvalidDurationException.class, ()->filmController.validateFilm(film4));
	}

}
