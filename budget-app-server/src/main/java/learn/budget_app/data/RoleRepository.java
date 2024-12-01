package learn.budget_app.data;

import learn.budget_app.models.Role;

import java.util.List;

public interface RoleRepository {

    List<Role> findAllRoles();

    Role findById(int id);

    Role findByName(String roleName);
}
