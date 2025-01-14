package learn.budget_app.data;

import learn.budget_app.data.mappers.CategoryMapper;
import learn.budget_app.data.mappers.ExpenseMapper;
import learn.budget_app.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository implements CategoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll(){
        final String sql = "select category_id, category_name "
                + "from category limit 1000;";
        return jdbcTemplate.query(sql, new CategoryMapper());
    }

    @Override
    @Transactional
    public Category findById(int categoryId) {

        final String sql = "select category_id, category_name "
                + "from category "
                + "where category_id = ?;";

        Category category = jdbcTemplate.query(sql, new CategoryMapper(), categoryId).stream()
                .findFirst().orElse(null);

        return category;
    }

    @Override
    public Category add(Category category) {

        final String sql = "insert into category (category_name) "
                + " values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getCategoryName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        category.setCategoryId(keyHolder.getKey().intValue());
        return category;
    }

    @Override
    public boolean update(Category category) {

        final String sql = "update category set "
                + "category_name = ? "
                + "where category_id = ?;";

        return jdbcTemplate.update(sql,
                category.getCategoryName(),
                category.getCategoryId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int categoryId) {

        if(!findReference(categoryId)){
            return jdbcTemplate.update("delete from category where category_id = ?;", categoryId) > 0;
        }else{
            return false;
        }
    }

    private boolean findReference(int categoryId){
        final String sql = "select expense_id, budget_id, category_id, date, "
                + "amount, recurring, recurring_timeframe "
                + "from expense "
                + "where category_id = ?;";

        return jdbcTemplate.query(sql, new ExpenseMapper(),categoryId)
                .stream()
                .findFirst().orElse(null) != null;
    }
}
