package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet resultSet, int i) throws SQLException{
        String name = resultSet.getString("FILM_NAME");
        String description = resultSet.getString("DESCRIPTION");
        LocalDate releaseDate = resultSet.getDate("RELEASE_DATE").toLocalDate();
        int duration = resultSet.getInt("DURATION");

        Film film = new Film(name, description, releaseDate, duration);
        film.setId(resultSet.getInt("FILM_ID"));
        film.setMpa(new MPA(resultSet.getInt("MPA_ID"), null));
        return film;
    }
}
