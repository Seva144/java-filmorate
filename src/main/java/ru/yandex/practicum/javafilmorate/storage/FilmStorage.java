package ru.yandex.practicum.javafilmorate.storage;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;

import java.util.LinkedHashSet;
import java.util.List;

public interface FilmStorage {

    List<Film> getAllFilms();

    int createFilm(Film film) throws ValidationException, NotFoundException;

    Film updateFilm(Film film) throws NotFoundException;

    Film getFilm(int id) throws NotFoundException;

    void addLike(int idFilm, int idUser);

    void deleteLike(int idFilm, int idUser);

    List<Film> getTopFilms(int count);

//    void addMpa(int idFilm, int idMPA);
//
//    void deleteMPA (int idFilm);

//    MPA getMpaForFilm(int filmId);

    MPA getMpa(int id);

    List<MPA> getAllMpa();

    void addGenre(int idFilm, int idGenres);

    void deleteGenre(int idFilm);

    Genre getGenre(int id);

    List<Genre> getGenres(int filmId);

    List<Genre> getAllGenre();
}
