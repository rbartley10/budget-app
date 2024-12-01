package learn.budget_app.data;

import learn.budget_app.data.mappers.IncomeMapper;
import learn.budget_app.models.Income;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class IncomeJdbcTemplateRepository implements IncomeRepository {

    private final JdbcTemplate jdbcTemplate;

    public IncomeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Income> findAll() {
        final String sql = "select * from income";

        return jdbcTemplate.query(sql, new IncomeMapper());
    }

    @Override
    @Transactional
    public Income findById(int incomeId) {
        final String sql = "select * from income where income_id = ?";

        return jdbcTemplate.query(sql, new IncomeMapper(), incomeId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Income add(Income income) {
        final String sql = "insert into income (user_id, amount, date, recurring_timeframe) "
                + "values (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, income.getUserId());
            ps.setBigDecimal(2, income.getAmount());
            ps.setDate(3, Date.valueOf(income.getDate()));
            ps.setString(4, income.getRecurringTimeframe().toString());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        income.setIncomeId(keyHolder.getKey().intValue());
        return income;
    }

    @Override
    public boolean update(Income income) {
        final String sql = "update income set "
                + "amount = ?, recurring_timeframe = ? "
                + "where income_id = ?";

        return jdbcTemplate.update(sql, income.getAmount(), income.getRecurringTimeframe().toString(), income.getIncomeId()) > 0;
    }

    @Override
    public boolean deleteById(int incomeId) {
        final String sql = "delete from income where income_id = ?";

        return jdbcTemplate.update(sql, incomeId) > 0;
    }
}