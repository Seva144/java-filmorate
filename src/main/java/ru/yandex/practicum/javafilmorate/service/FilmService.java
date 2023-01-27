package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage){
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> returnAllFilms(){
        return filmStorage.returnAllFilms();
    }

    public Film createFilm(Film film) throws ValidationException {
        return filmStorage.createFilm(film);
    }

    public Film changeFilm(Film film) throws Exception {
        return filmStorage.changeFilm(film);
    }

    public Film getFilm(int id) throws NotFoundException {
        return filmStorage.getFilm(id);
    }

    public void addLike(int idFilm, int idUser) throws NotFoundException {
        if(userStorage.getUser(idUser)!=null){
            getFilm(idFilm).getLikes().add(idUser);
            log.info("Фильм - " + getFilm(idFilm).getName() + "+1 лайк");
        } else {
            throw new NotFoundException();
        }
    }

    public void deleteLike(int idFilm, int idUser) throws NotFoundException {
        if(userStorage.getUser(idUser)!=null){
            getFilm(idFilm).getLikes().add(idUser);
            log.info("Фильм - " + getFilm(idFilm).getName() + "-1 лайк");
        } else {
            throw new NotFoundException();
        }
    }

    public List<Film> returnTopFilms(int count) {
        log.info("Показаны топ-фильмы");
        return returnAllFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    public int compare(Film film1, Film film2){
        return film2.getLikes().size()-film1.getLikes().size();
    }

}
