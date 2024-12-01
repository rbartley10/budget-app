package learn.budget_app.data.mappers;

import learn.budget_app.models.Budget;
import learn.budget_app.models.Expense;
import learn.budget_app.models.RecurringTimeframe;
import org.springframework.jdbc.core.RowMapper;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseMapper implements RowMapper<Expense> {
    @Override
    public Expense mapRow(ResultSet resultSet, int i) throws SQLException {
        Expense expense = new Expense();
        expense.setExpenseId(resultSet.getInt("expense_id"));
        expense.setBudgetId(resultSet.getInt("budget_id"));
        expense.setCategoryId(resultSet.getInt("category_id"));
        expense.setDate(resultSet.getDate("date").toLocalDate());
        expense.setAmount(resultSet.getBigDecimal("amount").setScale(2, RoundingMode.HALF_UP));
        expense.setRecurring(resultSet.getBoolean("recurring"));

        RecurringTimeframe recurringTimeframe = RecurringTimeframe.valueOf(resultSet.getString("recurring_timeframe"));
        expense.setRecurringTimeframe(recurringTimeframe);

        return expense;
    }
}
