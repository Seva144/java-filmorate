package ru.yandex.practicum.javafilmorate.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet resultSet, int i) throws SQLException{
        String nameFilm = resultSet.getString("FILM_NAME");
        String description = resultSet.getString("DESCRIPTION");
        LocalDate releaseDate = resultSet.getDate("RELEASE_DATE").toLocalDate();
        int duration = resultSet.getInt("DURATION");

        Film film = new Film(nameFilm, description, releaseDate, duration);

        film.setId(resultSet.getInt("FILM_ID"));

        if(resultSet.getString("TYPE_MPA")!=null){
            film.setMpa(new MPA(resultSet.getInt("MPA_ID")
                    ,resultSet.getString("TYPE_MPA")));
        }

        List<Genre> genres = new ArrayList<>();

        if(resultSet.getString("TYPE_ID")!=null) {

            List<Integer> typeId = Stream.of(resultSet.getString("TYPE_ID")
                    .split(",")).map(Integer::parseInt).collect(Collectors.toList());

            List<String> typeName = Stream.of(resultSet.getString("TYPE_NAME")
                    .split(",")).collect(Collectors.toList());


            for (int j = 0; j < typeName.size(); j++) {
                genres.add(new Genre(typeId.get(j), typeName.get(j)));
            }
        }

        film.setGenres(genres);

        return film;
    }
}
