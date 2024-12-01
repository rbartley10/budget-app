package learn.budget_app.data;

import learn.budget_app.models.Role;
import learn.budget_app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RoleJdbcTemplateTestRepository {


    @Autowired
    KnownGoodState knownGoodState;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    void shouldFindAll() {
        List<Role> roles = roleRepository.findAllRoles();

        assertTrue(roles.size() >= 3 && roles.size() <= 4);
    }

    @Test
    void shouldFindById() {
        Role role = roleRepository.findById(1);

        assertNotNull(role);
        assertEquals(1, role.getRoleId());
    }

    @Test
    void shouldNotFindById() {
        Role role = roleRepository.findById(5);
        assertNull(role);
    }

    @Test
    void shouldFindByName() {
        Role role = roleRepository.findByName("User");

        assertNotNull(role);
        assertEquals("User", role.getRoleName());
    }
}
