package ru.yandex.practicum.javafilmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.exceptions.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final static HashMap<Integer, Film> films = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(FilmStorage.class);
    private static int id;

    public int generateId() {
        return ++id;
    }

    public List<Film> returnAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Film createFilm(Film film) throws ValidationException {
        validateFilm(film);
        log.debug("Новый фильм добавлен " + film.getName());
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    public Film changeFilm(Film film) throws Exception {
        validateFilm(film);
        if (films.containsKey(film.getId())) {
            log.debug("Фильм обновлен " + film.getName());
            films.replace(film.getId(), film);
            return film;
        } else {
            throw new Exception();
        }
    }

    public Film getFilm(int id) throws NotFoundException {
        if(films.containsKey(id)){
            return films.get(id);
        } else {
            throw new NotFoundException();
        }
    }

    public void validateFilm(Film film) throws ValidationException {
        LocalDate trainArrival = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(trainArrival)) {
            log.debug("Дата релиза " + film.getReleaseDate());
            throw new ValidationException("Некорректная дата релиза");
        }
    }


}
