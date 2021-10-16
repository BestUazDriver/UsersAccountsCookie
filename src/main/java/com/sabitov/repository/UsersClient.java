package com.sabitov.repository;

import com.sabitov.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository("usersClient")
public class UsersClient {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersClient(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //language=SQL
    private static final String SQL_GET_ALL = "select * from users";

    public List<User> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, userRowMapper);
    }

    private RowMapper<User> userRowMapper = (rows, rowsNumber) -> {
        String login = rows.getString("login");
        String password = rows.getString("password");
        return new User(password, login);
    };
}
