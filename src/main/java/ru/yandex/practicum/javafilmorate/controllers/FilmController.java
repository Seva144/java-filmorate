package ru.yandex.practicum.javafilmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Показаны все фильмы");
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, NotFoundException {
        log.info("Фильм добавлен - " + film.getName());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film changeFilm(@Valid @RequestBody Film film) throws Exception {
        log.info("Фильм обновлен - " + film.getName());
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") Integer id) throws NotFoundException {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer filmId, @PathVariable("userId") Integer userId) throws NotFoundException {
        filmService.addLike(filmId, userId);
        log.info("+1 лайк фильму" + filmService.getFilm(filmId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) throws NotFoundException {
        filmService.deleteLike(id, userId);
        log.info("-1 лайк фильму" + filmService.getFilm(id));
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.getTopFilms(count);
    }

    @GetMapping( "/mpa")
    public List<MPA> getAllMpa() {
        return filmService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public MPA getMpa(@PathVariable("id") Integer id) throws NotFoundException {
        return filmService.getMpa(id);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenre(){
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable("id") Integer id) throws NotFoundException {
        return filmService.getGenre(id);
    }
}

