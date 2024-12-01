package learn.budget_app.domain;

import learn.budget_app.data.BudgetRepository;
import learn.budget_app.models.Budget;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BudgetServiceTest {

    @Autowired
    BudgetService service;

    @MockBean
    BudgetRepository repository;

    @Test
    void shouldFindAll(){
        when(repository.findAll()).thenReturn(getAll());

        List<Budget> all = service.findAll();
        assertNotNull(all);
        assertEquals(3,all.size());
        assertEquals(new Budget(3, 3, "To save for my children", true), all.get(2));
    }

    @Test
    void shouldFIndById(){
        when(repository.findById(1))
                .thenReturn(new Budget(1, 3, "To save for the future", true));
        Budget actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(new Budget(1, 3, "To save for the future", true),actual);
    }

    @Test
    void shouldAdd(){
        when(repository.add(makeBudget())).thenReturn(makeBudget());
        Result<Budget> result = service.add(makeBudget());
        assertTrue(result.isSuccess());
        assertEquals(makeBudget(),result.getPayload());
        assertEquals(makeBudget().getGoal(),result.getPayload().getGoal());
    }

    @Test
    void shouldNotAddNull(){
        when(repository.add(null)).thenReturn(null);
        Result<Budget> result = service.add(null);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("budget cannot be null",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutValidUserId(){
        when(repository.add(makeBudgetWithUserIdZero())).thenReturn(makeBudgetWithUserIdZero());
        Result<Budget> result = service.add(makeBudgetWithUserIdZero());
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("user id is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutGoal(){
        when(repository.add(makeBudgetWithoutGoal())).thenReturn(makeBudgetWithoutGoal());
        Result<Budget> result = service.add(makeBudgetWithoutGoal());
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("goal is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithIdSet(){
        Budget budget = makeBudget();
        budget.setBudgetId(1);
        when(repository.add(budget)).thenReturn(budget);
        Result<Budget> result = service.add(budget);

        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("budgetId cannot be set for `add` operation",result.getMessages().get(0));
    }

    @Test
    void shouldUpdate(){
        Budget budget = makeBudget();
        budget.setBudgetId(1);
        when(repository.update(budget)).thenReturn(true);
        Result<Budget> result = service.update(budget);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNull(){
        when(repository.update(null)).thenReturn(false);
        Result<Budget> result = service.update(null);

        assertFalse(result.isSuccess());
        assertEquals("budget cannot be null",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutValidUserId(){
        Budget budget = makeBudgetWithUserIdZero();
        budget.setBudgetId(1);
        when(repository.update(budget)).thenReturn(true);
        Result<Budget> result = service.update(budget);

        assertFalse(result.isSuccess());
        assertEquals("user id is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutGoal(){
        Budget budget = makeBudgetWithoutGoal();
        budget.setBudgetId(1);
        when(repository.update(budget)).thenReturn(true);
        Result<Budget> result = service.update(budget);

        assertFalse(result.isSuccess());
        assertEquals("goal is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExisting(){
        Budget budget = makeBudget();
        budget.setBudgetId(100000000);
        when(repository.update(budget)).thenReturn(false);
        Result<Budget> result = service.update(budget);

        assertFalse(result.isSuccess());
        assertEquals("budget with id 100000000, not found",result.getMessages().get(0));
    }

    @Test
    void shouldDeleteExisting(){
        when(repository.deleteById(1)).thenReturn(true);
        boolean actual = service.deleteById((1));

        assertTrue(actual);
    }

    @Test
    void shouldNotDeleteNoneExisting(){
        when(repository.deleteById(1000000)).thenReturn(false);
        boolean actual = service.deleteById((1000000));

        assertFalse(actual);
    }

    List<Budget> getAll(){
        List<Budget> all = new ArrayList<>();
        all.add(new Budget(1, 3, "To save for the future", true));
        all.add(new Budget(2, 3, "Test description", false));
        all.add(new Budget(3, 3, "To save for my children", true));
        return all;
    }

    Budget makeBudget(){
        Budget budget = new Budget();
        budget.setBudgetId(0);
        budget.setUserId(4);
        budget.setGoal("A new test goal");
        budget.setAllowAdvisor(true);

        return budget;
    }

    Budget makeBudgetWithUserIdZero(){
        Budget budget = new Budget();
        budget.setBudgetId(0);
        budget.setUserId(0);
        budget.setGoal("A new test goal");
        budget.setAllowAdvisor(true);

        return budget;
    }

    Budget makeBudgetWithoutGoal(){
        Budget budget = new Budget();
        budget.setBudgetId(0);
        budget.setUserId(4);
        budget.setGoal("");
        budget.setAllowAdvisor(true);

        return budget;
    }
}