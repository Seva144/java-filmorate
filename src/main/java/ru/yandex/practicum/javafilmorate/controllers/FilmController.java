package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidDescriptionException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidDurationException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidIdException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidLocalDateException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    protected int id;


    @GetMapping(value = "/films")
    public List<Film> returnAllFilms() {
        log.debug("Текущее количество постов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) {
        try {
            if (validateFilm(film)) {
                log.debug("Новый фильм добавлен " + film.getName());
                film.setId(generateId());
                films.put(film.getId(), film);

            }
        } catch (InvalidNameException | InvalidDescriptionException | InvalidLocalDateException |
                 InvalidDurationException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    @PutMapping(value = "/films")
    public Film changeFilm(@RequestBody Film film) {
        try {
            if (films.containsKey(film.getId())) {
                if (validateFilm(film)) {
                    log.debug("Фильм обновлен " + film.getName());
                    films.replace(film.getId(), film);
                }
            } else {
                throw new InvalidIdException("Неверный ID");
            }
        } catch (InvalidNameException | InvalidDescriptionException | InvalidLocalDateException |
                 InvalidDurationException | InvalidIdException e) {
            throw new RuntimeException(e);
        }
        return film;
    }

    public boolean validateFilm(Film film) throws InvalidNameException, InvalidDescriptionException,
            InvalidLocalDateException, InvalidDurationException {
        LocalDate trainArrival = LocalDate.of(1895, 12, 28);
        if ((film.getName() == null) || (film.getName().equals(""))) {
            log.debug("Пустое имя фильма");
            throw new InvalidNameException("Пустое имя фильма");
        } else if (film.getDescription().length() > 200) {
            log.debug("Описание фильма > 200 знаков " + film.getDescription().length());
            throw new InvalidDescriptionException("Описание фильма слишком длинное");
        } else if (film.getReleaseDate().isBefore(trainArrival)) {
            log.debug("Дата релиза " + film.getReleaseDate());
            throw new InvalidLocalDateException("Некорректная дата релиза");
        } else if (film.getDuration() < 0) {
            log.debug("Продолжительность фильма " + film.getDuration());
            throw new InvalidDurationException("Продолжительность фильма не может быть отрицательной");
        }
        return true;
    }

    public int generateId() {
        return ++id;
    }

}

