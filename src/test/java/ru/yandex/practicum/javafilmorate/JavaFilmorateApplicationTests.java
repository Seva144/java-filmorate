package ru.yandex.practicum.javafilmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.MPA;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.dao.FilmDbStorage;
import ru.yandex.practicum.javafilmorate.dao.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scheme.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clean_test.sql")
})
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    Film film1;
    Film film2;


    @Test
    public void Get_User() {

        User actual = userStorage.getUser(1);

        User expected = new User("USER1@MAIL.RU"
                , "USER_1", "USER1_LOGIN", LocalDate.of(2000, 1, 1));
        expected.setId(1);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void Get_All_Users() {

        List<User> actual = userStorage.getAllUsers();

        User user1 = new User("USER1@MAIL.RU"
                , "USER_1", "USER1_LOGIN", LocalDate.of(2000, 1, 1));
        User user2 = new User("USER2@MAIL.RU"
                , "USER_2", "USER2_LOGIN", LocalDate.of(1995, 1, 1));
        user1.setId(1);
        user2.setId(2);

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Create_User() {

        User expected = new User("USER3@MAIL.RU"
                , "USER_3", "USER3_LOGIN", LocalDate.of(2005, 1, 1));
        expected.setId(3);

        User actual = userStorage.createUser(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Update_User() {
        User expected = new User("USER2@YANDEX.RU"
                , "USER_2", "USER2_LOGIN", LocalDate.of(1995, 1, 1));
        expected.setId(2);

        User actual = userStorage.updateUser(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_Friends() {

        List<User> actual = userStorage.getFriends(1);

        User user2 = new User("USER2@MAIL.RU"
                , "USER_2", "USER2_LOGIN", LocalDate.of(1995, 1, 1));

        List<User> expected = new ArrayList<>();
        expected.add(user2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_All_films(){

        List<Film> expected = new ArrayList<>();

        Film film1 = new Film("FILM1", "DESCRIPTION1"
                , LocalDate.of(2000, 1,1), 120);
        film1.setMpa(new MPA(1, null));

        Film film2 = new Film("FILM2", "DESCRIPTION2"
                , LocalDate.of(1995, 5,5), 150);
        film2.setMpa(new MPA(2, null));

        expected.add(film1);
        expected.add(film2);

        List<Film> actual = filmStorage.getAllFilms();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_Film() {

        Film actual = filmStorage.getFilm(1);
        Film expected = new Film("FILM1", "DESCRIPTION1"
                , LocalDate.of(2000, 1,1), 120);
        film1.setMpa(new MPA(1, null));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Create_Film() {

        Film film3 = new Film("FILM3", "DESCRIPTION3"
                , LocalDate.of(2000, 1,1), 120);
        film1.setMpa(new MPA(3, null));

        Assertions.assertEquals(3, filmStorage.createFilm(film3));

    }

    @Test
    public void Update_Film() {
        Film expected = new Film("FILM2", "DESCRIPTION35"
                , LocalDate.of(1998, 5,5), 150);
        film2.setMpa(new MPA(2, null));

        Film actual = filmStorage.updateFilm(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_Top_Films(){
        Film film1 = new Film("FILM1", "DESCRIPTION1"
                , LocalDate.of(2000, 1,1), 120);
        film1.setMpa(new MPA(1, null));

        List<Film> expected = new ArrayList<>();
        expected.add(film1);

        List<Film> actual = filmStorage.getTopFilms(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void Get_Mpa() {

    }

    @Test
    public void Delete_Genre_toFilm() {

    }

    @Test
    public void Get_All_Genres() {

    }

    @Test
    public void Get_All_Genres_ofFilm() {

    }

    @Test
    public void Get_Genre() {

    }


}
