package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException{
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName(name);
        return genre;
    }
}
