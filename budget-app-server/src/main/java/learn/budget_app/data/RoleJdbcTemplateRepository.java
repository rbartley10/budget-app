package learn.budget_app.data;

import learn.budget_app.data.mappers.RoleMapper;
import learn.budget_app.data.mappers.UserMapper;
import learn.budget_app.models.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.stream;

@Repository
public class RoleJdbcTemplateRepository implements RoleRepository {


    private final JdbcTemplate jdbcTemplate;

    public RoleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Role> findAllRoles() {
        final String sql = "select * from role";

        return jdbcTemplate.query(sql, new RoleMapper());
    }

    @Override
    public Role findById(int roleId) {
        final String sql = "select * from role where role_id = ?";

        return jdbcTemplate.query(sql, new RoleMapper(), roleId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public Role findByName(String roleName) {
        final String sql = "select * from role where role_name = ?";

        return jdbcTemplate.query(sql, new RoleMapper(), roleName)
                .stream().findFirst().orElse(null);
    }
}
