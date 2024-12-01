package learn.budget_app.data;

import learn.budget_app.data.mappers.BudgetMapper;
import learn.budget_app.data.mappers.ExpenseMapper;
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
public class BudgetJdbcTemplateRepository implements BudgetRepository {
    private final JdbcTemplate jdbcTemplate;

    public BudgetJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Budget> findAll(){
        final String sql = "select budget_id, user_id, goal, allow_advisor "
                + "from budget limit 1000;";
        return jdbcTemplate.query(sql, new BudgetMapper());
    }

    @Override
    @Transactional
    public Budget findById(int budgetId) {

        final String sql = "select budget_id, user_id, goal, allow_advisor "
                + "from budget "
                + "where budget_id = ?;";

        Budget budget = jdbcTemplate.query(sql, new BudgetMapper(), budgetId).stream()
                .findFirst().orElse(null);

        if (budget != null) {
            addExpenses(budget);
        }

        return budget;
    }

    //find budgets by user ID (Method)


    @Override
    public Budget add(Budget budget) {

        final String sql = "insert into budget (user_id, goal, allow_advisor) "
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, budget.getUserId());
            ps.setString(2, budget.getGoal());
            ps.setBoolean(3, budget.isAllowAdvisor());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        budget.setBudgetId(keyHolder.getKey().intValue());
        return budget;
    }


    @Override
    public boolean update(Budget budget) {

        final String sql = "update budget set "
                + "user_id = ?, "
                + "goal = ?, "
                + "allow_advisor = ? "
                + "where budget_id = ?;";

        return jdbcTemplate.update(sql,
                budget.getUserId(),
                budget.getGoal(),
                budget.isAllowAdvisor(),
                budget.getBudgetId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int budgetId) {
        jdbcTemplate.update("delete from expense where budget_id = ?;", budgetId);
        return jdbcTemplate.update("delete from budget where budget_id = ?;", budgetId) > 0;
    }

    private void addExpenses(Budget budget){
        final String sql = "select e.expense_id, e.budget_id, e.category_id, e.date, e.amount, "
                + "e.recurring, e.recurring_timeframe "
                + "from expense e "
                + "where e.budget_id = ?;";

        var expenses = jdbcTemplate.query(sql, new ExpenseMapper(), budget.getBudgetId());
        budget.setExpenses(expenses);
    }
}
