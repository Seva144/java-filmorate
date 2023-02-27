package ru.yandex.practicum.javafilmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.mapper.FilmMapper;
import ru.yandex.practicum.javafilmorate.mapper.GenreMapper;
import ru.yandex.practicum.javafilmorate.mapper.MPAMapper;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.MPA;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Film> getAllFilms() {
        String sql = "select FILMS.FILM_ID as FILM_ID, " +
                "FILMS.FILM_NAME as FILM_NAME, " +
                "FILMS.DESCRIPTION as DESCRIPTION, " +
                "FILMS.RELEASE_DATE as RELEASE_DATE, " +
                "FILMS.DURATION as DURATION, " +
                "FILMS.MPA_ID as MPA_ID, " +
                "TYPE_MPA.NAME as TYPE_MPA, " +
                "GROUP_CONCAT(TYPE_ID) as TYPE_ID, " +
                "GROUP_CONCAT(TYPE_GENRES.NAME) as TYPE_NAME " +
                "from FILMS " +
                "left join GENRES on GENRES.FILM_ID = FILMS.FILM_ID " +
                "left join TYPE_GENRES on GENRES.TYPE_ID =  TYPE_GENRES.ID " +
                "left join TYPE_MPA on FILMS.MPA_ID = TYPE_MPA.ID " +
                "group by FILMS.FILM_ID";
        return jdbcTemplate.query(sql, new FilmMapper());
    }

    public Film getFilm(int idFilm) {
        String sql = "select FILMS.FILM_ID as FILM_ID, " +
                "FILMS.FILM_NAME as FILM_NAME, " +
                "FILMS.DESCRIPTION as DESCRIPTION, " +
                "FILMS.RELEASE_DATE as RELEASE_DATE, " +
                "FILMS.DURATION as DURATION, " +
                "FILMS.MPA_ID as MPA_ID, " +
                "TYPE_MPA.NAME as TYPE_MPA, " +
                "GROUP_CONCAT(TYPE_ID) as TYPE_ID, " +
                "GROUP_CONCAT(TYPE_GENRES.NAME) as TYPE_NAME " +
                "from FILMS " +
                "left join GENRES on GENRES.FILM_ID = FILMS.FILM_ID " +
                "left join TYPE_GENRES on GENRES.TYPE_ID =  TYPE_GENRES.ID " +
                "left join TYPE_MPA on FILMS.MPA_ID = TYPE_MPA.ID " +
                "group by FILMS.FILM_ID " +
                "having FILMS.FILM_ID = ?";
        return jdbcTemplate.queryForStream(sql, new FilmMapper(), idFilm)
                .findAny().orElse(null);
    }

    public int createFilm(Film film) {
        String sql = "insert into FILMS(FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)" +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        return (int) Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Film updateFilm(Film film) {
        int id = film.getId();
        String sql = "update FILMS set FILM_NAME=?, DESCRIPTION=?, " +
                "RELEASE_DATE=?, DURATION=?, MPA_ID=? where FILM_ID=?";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), id);
        return film;
    }

    public void addLike(int idFilm, int idUser) {
        String sql = "insert into LIKES(FILM_ID, USER_ID)" +
                "values (?, ?)";
        jdbcTemplate.update(sql, idFilm, idUser);
    }

    public void deleteLike(int idFilm, int idUser){
        String sql = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbcTemplate.update(sql, idFilm, idUser);
    }

    public List<Film> getTopFilms(int count) {
        String sql = "select FILMS.FILM_ID as FILM_ID, " +
                "FILMS.FILM_NAME as FILM_NAME, " +
                "FILMS.DESCRIPTION as DESCRIPTION, " +
                "FILMS.RELEASE_DATE as RELEASE_DATE, " +
                "FILMS.DURATION as DURATION, " +
                "FILMS.MPA_ID as MPA_ID, " +
                "TYPE_MPA.NAME as TYPE_MPA, " +
                "GROUP_CONCAT(TYPE_ID) as TYPE_ID, " +
                "GROUP_CONCAT(TYPE_GENRES.NAME) as TYPE_NAME " +
                "from FILMS " +
                "left join LIKES on FILMS.FILM_ID = LIKES.FILM_ID " +
                "left join GENRES on GENRES.FILM_ID = FILMS.FILM_ID " +
                "left join TYPE_GENRES on GENRES.TYPE_ID =  TYPE_GENRES.ID " +
                "left join TYPE_MPA on FILMS.MPA_ID = TYPE_MPA.ID " +
                "group by FILMS.FILM_ID " +
                "order by COUNT(LIKES.USER_ID) desc";
        return jdbcTemplate.query(sql, new FilmMapper());
    }

    public MPA getMpa(int idMPA) {
        String sql = "select * from TYPE_MPA " +
                "where ID = ?";
        return jdbcTemplate.queryForStream(sql, new MPAMapper(), idMPA)
                .findAny().orElse(null);
    }

    public List<MPA> getAllMpa() {
        String sql = "select * from TYPE_MPA";
        return jdbcTemplate.query(sql, new MPAMapper());
    }

    public void addGenre(int idFilm, int idGenres) {
        String sql = "insert into GENRES(FILM_ID, TYPE_ID) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, idFilm, idGenres);
    }

    public void deleteGenre(int idFilm) {
        String sql = "delete from GENRES where FILM_ID = ?";
        jdbcTemplate.update(sql, idFilm);
    }

    public List<Genre> getGenres(int idFilm) {
        String sql = "select * from TYPE_GENRES " +
                "inner join GENRES on GENRES.TYPE_ID = TYPE_GENRES.ID " +
                "where GENRES.FILM_ID = ?";
        return jdbcTemplate.query(sql, new GenreMapper(), idFilm);
    }

    public Genre getGenre(int idGenre) {
        String sql = "select * from TYPE_GENRES " +
                "where ID = ?";
        return jdbcTemplate.query(sql, new GenreMapper(), idGenre).stream()
                .findAny().orElse(null);
    }

    public List<Genre> getAllGenre() {
        String sql = "select * from TYPE_GENRES";
        return jdbcTemplate.query(sql, new GenreMapper());
    }
}

