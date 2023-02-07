package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

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

    private Set<Integer> likes = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}

