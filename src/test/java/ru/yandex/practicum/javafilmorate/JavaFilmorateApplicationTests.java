package ru.yandex.practicum.javafilmorate;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FilmDbStorage;
import ru.yandex.practicum.javafilmorate.storage.UserDbStorage;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;

	User user1;
	User user2;
	User user3;

	Film film1;
	Film film2;
	Film film3;



	@Test
	public void Get_Film() {

		User user = userStorage.getUser(1);

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
	public void Create_Film(){

	}

	@Test
	public void Update_Film(){

	}

	@Test
	public void Add_Like(){

	}

	@Test
	public void Delete_Like(){

	}



}
