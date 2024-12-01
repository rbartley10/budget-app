package learn.budget_app.data;

import learn.budget_app.data.mappers.BudgetMapper;
import learn.budget_app.data.mappers.UserMapper;
import learn.budget_app.models.User;
import learn.budget_app.models.Budget;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        final String sql = "select * from user";

        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Transactional
    public User findById(int userId) {

        final String sql = "select * from user where user_id = ?";

        return jdbcTemplate.query(sql, new UserMapper(), userId)
                .stream().findFirst().orElse(null);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        final String sql = "select * from user where username = ?";

        return jdbcTemplate.query(sql, new UserMapper(), username)
                .stream().findFirst().orElse(null);
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user (first_name, last_name, username, password, balance, role_id) "
                + "values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.setBigDecimal(5, user.getBalance());
            ps.setInt(6, user.getRoleId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set " +
                "balance = ? " +
                "where user_id = ?";

        return jdbcTemplate.update(sql, user.getBalance(), user.getUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int userId) {
        List<Budget> budgets = jdbcTemplate.query("select * from budget where user_id = ?", new BudgetMapper(), userId);

        if (!budgets.isEmpty()) {
            for (Budget budget : budgets) {
                jdbcTemplate.update("delete from expense where budget_id = ?", budget.getBudgetId());
            }
            jdbcTemplate.update("delete from budget where user_id = ?", userId);
        }

        jdbcTemplate.update("delete from income where user_id = ?", userId);

        return jdbcTemplate.update("delete from user where user_id = ?", userId) > 0;
    }
}