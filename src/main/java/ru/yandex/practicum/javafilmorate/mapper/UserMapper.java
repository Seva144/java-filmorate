package ru.yandex.practicum.javafilmorate.mapper;

import ru.yandex.practicum.javafilmorate.model.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        String email = resultSet.getString("EMAIL");
        String name = resultSet.getString("USER_NAME");
        String login = resultSet.getString("LOGIN");
        LocalDate birthday = resultSet.getDate("BIRTHDAY").toLocalDate();
        User user = new User(email, name, login, birthday);
        user.setId(resultSet.getInt("USER_ID"));
        return user;
    }

}
