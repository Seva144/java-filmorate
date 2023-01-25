package ru.yandex.practicum.javafilmorate.util;

import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

public class FilmorateUtil {

    public static FilmStorage getDefaultFilmStorage(){
        return new InMemoryFilmStorage();
    }

    public static UserStorage getDefaultUserStorage(){
        return new InMemoryUserStorage();
    }
}
