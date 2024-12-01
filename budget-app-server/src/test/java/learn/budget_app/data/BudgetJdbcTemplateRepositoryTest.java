package learn.budget_app.data;

import learn.budget_app.models.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BudgetJdbcTemplateRepositoryTest {

    @Autowired
    BudgetJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Budget> budgets = repository.findAll();

        assertNotNull(budgets);
        assertEquals(new Budget(1, 3, "To save for the future", true), budgets.get(0));
        assertTrue(budgets.size() >= 5);
    }

    @Test
    void shouldFindById(){
        Budget budget = repository.findById(3);

        Budget expected = new Budget(3, 3, "To save for my children", true);

        assertNotNull(budget);
        assertEquals(expected,budget);
    }

    @Test
    void shouldNotFind(){
        Budget budget = repository.findById(-999);

        assertNull(budget);
    }

    @Test
    void shouldAdd(){
        Budget budget = new Budget();
        budget.setUserId(3);
        budget.setGoal("Save a lot");
        budget.setAllowAdvisor(false);

        Budget actual = repository.add(budget);
        assertNotNull(actual);
        assertEquals(budget, actual);
    }

    @Test
    void shouldUpdateExisting(){
        Budget budget = new Budget(1, 3, "To save for the future", true);
        boolean actual = repository.update(budget);
        Budget updated = repository.findById(1);
        assertTrue(actual);
        assertEquals(budget, updated);

    }

    @Test
    void shouldNotUpdateNonExisting(){
        Budget budget = new Budget(-999,1,"New Test Description",true);

        boolean actual = repository.update(budget);
        assertFalse(actual);
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(6));
    }

    @Test
    void shouldNotDelete(){
        assertFalse(repository.deleteById(-99999));
    }
}