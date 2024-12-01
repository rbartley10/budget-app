package learn.budget_app.data;

import learn.budget_app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryJdbcTemplateTestRepository {

    @Autowired
    KnownGoodState knownGoodState;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<User> users = userRepository.findAll();

        assertTrue(users.size() >= 3 && users.size() <= 4);
    }

    @Test
    void shouldFindById() {
        User user = this.userRepository.findById(1);

        assertNotNull(user);
        assertEquals(1, user.getUserId());
    }

    @Test
    void shouldNotFindById() {
        User user = this.userRepository.findById(5);

        assertNull(user);
    }

    @Test
    void shouldAddUser() {
        BigDecimal amount = new BigDecimal(3);
        User user = new User();
        user.setUserId(4);
        user.setFirstName("Joshua");
        user.setLastName("Martin");
        user.setUserName("joshuallemart");
        user.setPassword("top-secret-password");
        user.setBalance(new BigDecimal(3));
        user.setRoleId(1);

        User expected = this.userRepository.add(user);
        assertNotNull(expected);
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), expected.getBalance());
    }

    @Test
    void shouldUpdateBalance() {
        BigDecimal amount = new BigDecimal(5);
        User user = new User();
        user.setUserId(4);
        user.setBalance(new BigDecimal(5));

        boolean success = this.userRepository.update(user);
        assertTrue(success);
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), user.getBalance());
    }

    @Test
    void shouldNotUpdateBalance() {
        BigDecimal amount = new BigDecimal(5);
        User user = new User();
        user.setUserId(5);
        user.setBalance(new BigDecimal(5));

        boolean success = this.userRepository.update(user);
        assertFalse(success);
    }

    @Test
    void shouldDeleteUser() {
        boolean success = userRepository.deleteById(1);

        assertTrue(success);
    }

    @Test
    void shouldNotDeleteUser() {
        boolean success = userRepository.deleteById(5);

        assertFalse(success);
    }
}
