package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.dao.UserMapper;
import ru.yandex.practicum.javafilmorate.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers() {
        String sql = "select * from USERS";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    public User getUser(int id) {
        String sql = "select * from USERS where USER_ID = ?";
        return jdbcTemplate.queryForStream(sql, new UserMapper(), id)
                .findAny().orElse(null);

    }

    public User createUser(User user) {
        String sql = "insert into USERS(USER_NAME, EMAIL, BIRTHDAY, LOGIN)" +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"USER_ID"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setDate(3, java.sql.Date.valueOf(user.getBirthday()));
            stmt.setString(4, user.getLogin());
            return stmt;
        }, keyHolder);
        int id = (int) Objects.requireNonNull(keyHolder.getKey()).longValue();
        user.setId(id);
        return user;
    }

    public User updateUser(User user) {
        int id = user.getId();
        if (getUser(id) != null) {
            String sql = "update USERS set USER_NAME=?, EMAIL=?, " +
                    "BIRTHDAY=?, LOGIN=? where USER_ID=?";
            jdbcTemplate.update(sql, user.getName(), user.getEmail(),
                    user.getBirthday(), user.getLogin(), id);
            return user;
        } else {
            return null;
        }
    }

    public void addFriend(int id, int idFriend) {
        String sql = "insert into FRIENDSHIP (USER_ID, FRIEND_ID) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, id, idFriend);
    }

    public void deleteFriend(int id, int idFriend) {
        String sql = "delete from FRIENDSHIP where USER_ID = ? and FRIEND_ID = ?";
        jdbcTemplate.update(sql, id, idFriend);
    }

    public List<User> getFriends(int id) {
        String sql = "select * from USERS " +
                "inner join FRIENDSHIP on USERS.USER_ID = FRIENDSHIP.FRIEND_ID " +
                "where FRIENDSHIP.USER_ID = ? ";
        return jdbcTemplate.query(sql, new UserMapper(), id);
    }

}


