package learn.budget_app.domain;

import learn.budget_app.data.UserRepository;
import learn.budget_app.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AppUserRepositoryServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void shouldAdd() {
        User testUser = new User();
        testUser.setUserId(0);
        testUser.setFirstName("Joshua");
        testUser.setLastName("Martin");
        testUser.setUserName("joshuallemart");
        testUser.setPassword("top-secret-password");
        testUser.setBalance(new BigDecimal(3));
        testUser.setRoleId(1);

        User actual = new User();
        actual.setUserId(4);
        actual.setFirstName("Joshua");
        actual.setLastName("Martin");
        actual.setUserName("joshuallemart");
        actual.setPassword("top-secret-password");
        actual.setBalance(new BigDecimal(3));
        actual.setRoleId(1);

        when(this.userRepository.add(testUser)).thenReturn(actual);
        Result<User> result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.SUCCESS);
    }

    @Test
    void shouldNotAddInvalid() {
        User testUser = new User();
        testUser.setFirstName("Joshua");
        testUser.setLastName("Martin");
        testUser.setUserName("joshuallemart");
        testUser.setPassword("top-secret-password");
        testUser.setBalance(new BigDecimal(3));
        testUser.setRoleId(1);

        // bad id
        testUser.setUserId(1);
        Result<User> result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad first name
        testUser.setUserId(0);
        testUser.setFirstName("");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad last name
        testUser.setFirstName("Joshua");
        testUser.setLastName("");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad username
        testUser.setLastName("Martin");
        testUser.setUserName("");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 2);

        testUser.setUserName("toolongofausernameforourservice");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        testUser.setUserName("jjj");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad password
        testUser.setUserName("joshuallemart");
        testUser.setPassword("");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 2);

//        testUser.setPassword("thispasswordisverylikelytoolongforthisservice");
//        result = userService.add(testUser);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);

        testUser.setPassword("short");
        result = userService.add(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldUpdate() {
        User testUser = new User();
        testUser.setUserId(1);
        testUser.setFirstName("Joshua");
        testUser.setLastName("Martin");
        testUser.setUserName("joshuallemart");
        testUser.setPassword("top-secret-password");
        testUser.setBalance(new BigDecimal(3));
        testUser.setRoleId(1);

        when(this.userRepository.update(testUser)).thenReturn(true);
        Result<User> result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.SUCCESS);
    }

    @Test
    void shouldNotUpdateInvalid() {
        User testUser = new User();
        testUser.setFirstName("Joshua");
        testUser.setLastName("Martin");
        testUser.setUserName("joshuallemart");
        testUser.setPassword("top-secret-password");
        testUser.setBalance(new BigDecimal(3));
        testUser.setRoleId(1);

        // bad first name
        testUser.setUserId(0);
        testUser.setFirstName("");
        Result<User> result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad last name
        testUser.setFirstName("Joshua");
        testUser.setLastName("");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad username
        testUser.setLastName("Martin");
        testUser.setUserName("");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 2);

        testUser.setUserName("toolongofausernameforourservice");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        testUser.setUserName("jjj");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);

        // bad password
        testUser.setUserName("joshuallemart");
        testUser.setPassword("");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 2);

//        testUser.setPassword("thispasswordisverylikelytoolongforthisservice");
//        result = userService.update(testUser);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);

        testUser.setPassword("short");
        result = userService.update(testUser);
        assertEquals(result.getType(), ResultType.INVALID);
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldDelete() {
        int userId = 1;
        when(userRepository.deleteById(userId)).thenReturn(true);
        assertTrue(userService.deleteById(userId));
    }

    @Test
    void shouldNotDelete() {
        int userId = 5;
        when(userRepository.deleteById(userId)).thenReturn(false);
        assertFalse(userService.deleteById(userId));
    }
}
