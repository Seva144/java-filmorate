package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Data
public class Film {

    private int id;
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    @Past
    private final LocalDate releaseDate;
    @Positive
    private final int duration;

    private MPA mpa;

    private List<Genre> genres = new ArrayList<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}

