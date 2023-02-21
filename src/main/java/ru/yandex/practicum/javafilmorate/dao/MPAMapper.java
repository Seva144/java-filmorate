package ru.yandex.practicum.javafilmorate.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.javafilmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MPAMapper implements RowMapper<MPA> {

    @Override
    public MPA mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("ID");
        String name = resultSet.getString("NAME");
        return new MPA(id,name);
    }

}
