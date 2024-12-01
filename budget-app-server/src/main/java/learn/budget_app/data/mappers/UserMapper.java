package learn.budget_app.data.mappers;

import learn.budget_app.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setBalance(resultSet.getBigDecimal("balance"));
        user.setRoleId(resultSet.getInt("role_id"));
        user.setDisabled(resultSet.getBoolean("disabled"));
        return user;
    }
}