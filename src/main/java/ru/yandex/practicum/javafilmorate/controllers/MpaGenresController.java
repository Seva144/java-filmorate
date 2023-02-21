package ru.yandex.practicum.javafilmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.javafilmorate.exceptions.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;
import ru.yandex.practicum.javafilmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j

public class MpaGenresController {

    private final FilmService filmService;

    @Autowired
    public MpaGenresController(FilmService filmService) {
        this.filmService = filmService;
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
