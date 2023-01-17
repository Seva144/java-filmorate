package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/films")
public class FilmController {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);
    protected int id;

    @GetMapping
    public List<Film> returnAllFilms() {
        log.debug("Текущее количество постов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateFilm(film);
        log.debug("Новый фильм добавлен " + film.getName());
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film changeFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            log.debug("Фильм обновлен " + film.getName());
            films.replace(film.getId(), film);
        } else {
            throw new ValidationException("Неверный ID");
        }
        return film;
    }

    public void validateFilm(Film film) throws ValidationException {
        LocalDate trainArrival = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(trainArrival)) {
            log.debug("Дата релиза " + film.getReleaseDate());
            throw new ValidationException("Некорректная дата релиза");
        }
    }

    public int generateId() {
        return ++id;
    }

}

