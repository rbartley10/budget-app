package learn.budget_app.data.mappers;

import learn.budget_app.models.Income;
import learn.budget_app.models.RecurringTimeframe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeMapper implements RowMapper<Income> {

    @Override
    public Income mapRow(ResultSet resultSet, int i) throws SQLException {
        Income income = new Income();
        income.setIncomeId(resultSet.getInt("income_id"));
        income.setUserId(resultSet.getInt("user_id"));
        income.setAmount(resultSet.getBigDecimal("amount"));
        income.setDate(resultSet.getDate("date").toLocalDate());
        income.setRecurringTimeframe(RecurringTimeframe.valueOf(resultSet.getString("recurring_timeframe")));
        return income;
    }
}