package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {

    private int id;
  @NotNull
    private final String name;
    @Max(10)
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}

