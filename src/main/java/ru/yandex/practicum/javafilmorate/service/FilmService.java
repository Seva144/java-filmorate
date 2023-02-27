package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(@Qualifier(value = "filmDbStorage") FilmStorage filmStorage,
                       @Qualifier(value = "userDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilm(int idFilm) throws NotFoundException {
        return Optional.ofNullable(filmStorage.getFilm(idFilm)).orElseThrow(NotFoundException::new);
    }

    public Film createFilm(Film film) throws ValidationException, NotFoundException {
        validateFilm(film);
        int idFilm = filmStorage.createFilm(film);
        if (film.getGenres() != null) {
            film.getGenres()
                    .forEach(genre -> filmStorage.addGenre(idFilm, genre.getId()));
        }
        return getFilm(idFilm);
    }

    public Film updateFilm(Film film) throws NotFoundException {
        if (filmStorage.getFilm(film.getId()) != null) {
            filmStorage.updateFilm(film);
            filmStorage.deleteGenre(film.getId());
            if (film.getGenres() != null) {
                film.getGenres().stream().distinct()
                        .forEach(genre -> filmStorage.addGenre(film.getId(), genre.getId()));
            }
            return getFilm(film.getId());
        } else {
            throw new NotFoundException();
        }
    }

    public void addLike(int idFilm, int idUser) throws NotFoundException {
        if (userStorage.getUser(idUser) != null) {
            filmStorage.addLike(idFilm, idUser);
        } else {
            throw new NotFoundException();
        }
    }

    public void deleteLike(int idFilm, int idUser) throws NotFoundException {
        if (userStorage.getUser(idUser) != null) {
            filmStorage.deleteLike(idFilm, idUser);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Film> getTopFilms(int count) {

        List<Film> films = filmStorage.getTopFilms(count).stream().limit(count)
                .collect(Collectors.toList());
        log.info("Показаны топ-фильмы");
        return films;
    }

    public MPA getMpa(int id) throws NotFoundException {
        return Optional.ofNullable(filmStorage.getMpa(id)).orElseThrow(NotFoundException::new);
    }

    public List<MPA> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Genre getGenre(int idFilm) throws NotFoundException {
        return Optional.ofNullable(filmStorage.getGenre(idFilm)).orElseThrow(NotFoundException::new);
    }

    public List<Genre> getAllGenres() {
        return filmStorage.getAllGenre();
    }

    public void validateFilm(Film film) throws ValidationException {
        LocalDate trainArrival = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(trainArrival)) {
            log.debug("Дата релиза " + film.getReleaseDate());
            throw new ValidationException("Некорректная дата релиза");
        }
    }

}


