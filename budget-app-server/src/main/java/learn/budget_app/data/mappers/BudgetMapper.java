package learn.budget_app.data.mappers;

import learn.budget_app.models.Budget;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetMapper implements RowMapper<Budget> {

    @Override
    public Budget mapRow(ResultSet resultSet, int i) throws SQLException {
        Budget budget = new Budget();
        budget.setBudgetId(resultSet.getInt("budget_id"));
        budget.setUserId(resultSet.getInt("user_id"));
        budget.setGoal(resultSet.getString("goal"));
        budget.setAllowAdvisor(resultSet.getBoolean("allow_advisor"));

        return budget;
    }
}
