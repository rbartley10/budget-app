package learn.budget_app.data;

import learn.budget_app.data.mappers.ExpenseMapper;
import learn.budget_app.models.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ExpenseJdbcTemplateRepository implements ExpenseRepository {
    private final JdbcTemplate jdbcTemplate;

    public ExpenseJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Expense> findAll(){
        final String sql = "select expense_id, budget_id, category_id, date, amount, "
                + "recurring, recurring_timeframe "
                + "from expense limit 1000;";
        return jdbcTemplate.query(sql, new ExpenseMapper());
    }


    @Override
    @Transactional
    public Expense findById(int expenseId) {

        final String sql = "select expense_id, budget_id, category_id, date, amount, "
                + "recurring, recurring_timeframe "
                + "from expense "
                + "where expense_id = ?;";

        Expense expense = jdbcTemplate.query(sql, new ExpenseMapper(), expenseId).stream()
                .findFirst().orElse(null);

        return expense;
    }


    @Override
    public Expense add(Expense expense) {

        final String sql = "insert into expense (budget_id, category_id, date, amount, recurring, recurring_timeframe) "
                + "values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, expense.getBudgetId());
            ps.setInt(2, expense.getCategoryId());
            ps.setDate(3, Date.valueOf(expense.getDate()));
            ps.setBigDecimal(4,expense.getAmount());
            ps.setBoolean(5,expense.isRecurring());
            ps.setString(6, expense.getRecurringTimeframe().toString());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        expense.setExpenseId(keyHolder.getKey().intValue());
        return expense;
    }

    @Override
    public boolean update(Expense expense) {

        final String sql = "update expense set "
                + "budget_id = ?, "
                + "category_id = ?, "
                + "date = ?, "
                + "amount = ?, "
                + "recurring = ?, "
                + "recurring_timeframe = ? "
                + "where expense_id = ?;";

        return jdbcTemplate.update(sql,
                expense.getBudgetId(),
                expense.getCategoryId(),
                expense.getDate(),
                expense.getAmount(),
                expense.isRecurring(),
                expense.getRecurringTimeframe().toString(),
                expense.getExpenseId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int expenseId) {
        return jdbcTemplate.update("delete from expense where expense_id = ?;", expenseId) > 0;
    }
}
