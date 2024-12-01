package learn.budget_app.data;

import learn.budget_app.models.Expense;
import learn.budget_app.models.RecurringTimeframe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpenseJdbcTemplateRepositoryTest {
    @Autowired
    ExpenseJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll(){
        List<Expense> expenses = repository.findAll();

        assertNotNull(expenses);
        System.out.println(expenses.get(0));
        assertEquals(new Expense(1, 2, 4, LocalDate.of(2020, 4, 22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("NONE")), expenses.get(0));
        assertTrue(expenses.size() >= 2);
    }

    @Test
    void shouldFindById(){
        Expense expense = repository.findById(1);

        Expense expected = new Expense(1, 2, 4, LocalDate.of(2020, 4, 22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("NONE"));

        assertNotNull(expense);
        assertEquals(expected,expense);
    }

    @Test
    void shouldNotFind(){
        Expense expense = repository.findById(-999);

        assertNull(expense);
    }

    @Test
    void shouldAdd(){
        Expense expected = new Expense();

        expected.setBudgetId(2);
        expected.setCategoryId(1);
        expected.setDate(LocalDate.of(2020,5,27));
        expected.setAmount(BigDecimal.valueOf(11).setScale(2, RoundingMode.HALF_UP));
        expected.setRecurring(false);
        expected.setRecurringTimeframe(RecurringTimeframe.valueOf("NONE"));

        Expense actual = repository.add(expected);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateExisting(){
        Expense expense = new Expense(2, 2, 4, LocalDate.of(2020, 10, 22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("WEEKLY"));
        boolean actual = repository.update(expense);
        Expense updated = repository.findById(2);

        assertTrue(actual);
        assertEquals(expense,updated);
    }

    @Test
    void shouldNotUpdateNonExisting(){
        Expense expense = new Expense(-10,2, 4, LocalDate.of(2020, 10, 22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("WEEKLY"));

        boolean actual = repository.update(expense);
        assertFalse(actual);
    }

    @Test
    void shouldDelete(){
        assertTrue(repository.deleteById(3));
    }

    @Test
    void shouldNotDelete(){
        assertFalse(repository.deleteById(-99999));
    }

}