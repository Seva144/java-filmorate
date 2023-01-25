package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Показаны все фильмы");
        return filmService.returnAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Фильм добавлен - " + film.getName());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film changeFilm(@Valid @RequestBody Film film) throws Exception {
        log.info("Фильм обновлен - " + film.getName());
        return filmService.changeFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") Integer id) throws NotFoundException {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) throws NotFoundException {
        filmService.addLike(id, userId);
        log.info("+1 лайк фильму" + filmService.getFilm(id));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) throws NotFoundException {
        filmService.deleteLike(id, userId);
        log.info("-1 лайк фильму" + filmService.getFilm(id));
    }

    @GetMapping("/popular")
    public List<Film> returnTopFilms(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.returnTopFilms(count);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        return new ErrorResponse(
                "Ошибка c параметром id", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFound(final NotFoundException e){
        return new ErrorResponse(
                "Такой пользователь не найден", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Exception e){
        return new ErrorResponse(
                "Непредвиденная ошибка", e.getMessage()
        );
    }
}

