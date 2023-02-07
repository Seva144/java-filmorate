package ru.yandex.practicum.javafilmorate.storage;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> returnAllFilms();

    Film createFilm(Film film) throws ValidationException;

    Film changeFilm(Film film) throws Exception;

    Film getFilm(int id) throws NotFoundException;

}
