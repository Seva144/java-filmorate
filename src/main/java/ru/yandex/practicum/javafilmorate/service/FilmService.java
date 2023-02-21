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
        List<Film> films = filmStorage.getAllFilms();
        films.forEach(film -> film.setMpa(filmStorage.getMpa(film.getMpa().getId())));
        films.forEach(film -> film.setGenres(filmStorage.getGenres(film.getId())));
        return films;
    }

    public Film getFilm(int idFilm) throws NotFoundException {
        if (filmStorage.getFilm(idFilm) != null) {
            Film film = filmStorage.getFilm(idFilm);
            int idMPA = film.getMpa().getId();
            film.setMpa(filmStorage.getMpa(idMPA));
            film.setGenres(filmStorage.getGenres(idFilm));
            return film;
        } else {
            throw new NotFoundException();
        }
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
        films.forEach(film -> film.setMpa(filmStorage.getMpa(film.getMpa().getId())));
        films.forEach(film -> film.setGenres(filmStorage.getGenres(film.getId())));
        log.info("Показаны топ-фильмы");
        return films;
    }

    public MPA getMpa(int id) throws NotFoundException {
        if(filmStorage.getMpa(id)!=null){
            return filmStorage.getMpa(id);
        } else {
            throw new NotFoundException();
        }
    }

    public List<MPA> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Genre getGenre(int idFilm) throws NotFoundException {
        if(filmStorage.getGenre(idFilm)!=null){
            return filmStorage.getGenre(idFilm);
        } else {
            throw new NotFoundException();
        }
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
