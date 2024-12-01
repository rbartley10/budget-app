package learn.budget_app.domain;

import learn.budget_app.data.ExpenseRepository;
import learn.budget_app.models.Budget;
import learn.budget_app.models.Expense;
import learn.budget_app.models.RecurringTimeframe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExpenseServiceTest {
    @Autowired
    ExpenseService service;

    @MockBean
    ExpenseRepository repository;

    @Test
    void shouldFindAll(){
        when(repository.findAll()).thenReturn(getAll());

        List<Expense> all = service.findAll();
        assertNotNull(all);
        assertEquals(3,all.size());
        assertEquals(new Expense(3, 3, 2, LocalDate.of( 2020,4,22),
                BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP), true,
                RecurringTimeframe.valueOf("WEEKLY")),
                all.get(2));
    }

    @Test
    void shouldFIndById(){
        when(repository.findById(1))
                .thenReturn(new Expense(1, 2, 4, LocalDate.of( 2020,4,22),
                        BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                        RecurringTimeframe.valueOf("NONE")));
        Expense actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(new Expense(1, 2, 4, LocalDate.of( 2020,4,22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("NONE")),actual);
    }

    @Test
    void shouldAdd(){
        when(repository.add(makeExpense())).thenReturn(makeExpense());
        Result<Expense> result = service.add(makeExpense());
        assertTrue(result.isSuccess());
        assertEquals(makeExpense(),result.getPayload());
        assertEquals(makeExpense().getBudgetId(),result.getPayload().getBudgetId());
    }

    @Test
    void shouldNotAddNull(){
        when(repository.add(null)).thenReturn(null);
        Result<Expense> result = service.add(null);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("expense cannot be null",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutValidBudgetId(){
        Expense expense = makeExpense();
        expense.setBudgetId(0);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("budget id is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutValidCategoryId(){
        Expense expense = makeExpense();
        expense.setCategoryId(0);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("category id is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutDate(){
        Expense expense = makeExpense();
        expense.setDate(null);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("date is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutAmount(){
        Expense expense = makeExpense();
        expense.setAmount(null);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("amount is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutTimeframe(){
        Expense expense = makeExpense();
        expense.setRecurringTimeframe(null);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("timeframe is required",result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithIdSet(){
        Expense expense = makeExpense();
        expense.setExpenseId(4);
        when(repository.add(expense)).thenReturn(expense);
        Result<Expense> result = service.add(expense);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertEquals("expenseId cannot be set for `add` operation",result.getMessages().get(0));
    }

    @Test
    void shouldUpdate(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateNull(){
        when(repository.update(null)).thenReturn(false);
        Result<Expense> result = service.update(null);
        assertFalse(result.isSuccess());
        assertEquals("expense cannot be null", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutValidBudgetId(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        expense.setBudgetId(0);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("budget id is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutValidCategoryId(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        expense.setCategoryId(0);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("category id is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutDate(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        expense.setDate(null);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("date is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutAmount(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        expense.setAmount(null);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("amount is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutTimeframe(){
        Expense expense = makeExpense();
        expense.setExpenseId(3);
        expense.setRecurringTimeframe(null);
        when(repository.update(expense)).thenReturn(true);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("timeframe is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutIdSet(){
        Expense expense = makeExpense();
        expense.setExpenseId(0);
        when(repository.update(expense)).thenReturn(false);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("expense id must be set for `update` operation", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExisting(){
        Expense expense = makeExpense();
        expense.setExpenseId(9999999);
        when(repository.update(expense)).thenReturn(false);
        Result<Expense> result = service.update(expense);
        assertFalse(result.isSuccess());
        assertEquals("expense with id 9999999, not found", result.getMessages().get(0));
    }

    @Test
    void shouldDelete(){
        when(repository.deleteById(2)).thenReturn(true);
        boolean actual = service.deleteById(2);
        assertTrue(actual);
    }

    @Test
    void shouldNotDeleteNonExisting(){
        when(repository.deleteById(7777777)).thenReturn(false);
        boolean actual = service.deleteById(7777777);
        assertFalse(actual);
    }

    List<Expense> getAll(){
        List<Expense> all = new ArrayList<>();
        all.add(new Expense(1, 2, 4, LocalDate.of( 2020,4,22),
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), false,
                RecurringTimeframe.valueOf("NONE")));
        all.add(new Expense(2, 2, 1, LocalDate.of( 2020,5,23),
                BigDecimal.valueOf(14.99).setScale(2, RoundingMode.HALF_UP), true,
                RecurringTimeframe.valueOf("MONTHLY")));
        all.add(new Expense(3, 3, 2, LocalDate.of( 2020,4,22),
                BigDecimal.valueOf(25).setScale(2, RoundingMode.HALF_UP), true,
                RecurringTimeframe.valueOf("WEEKLY")));
        return all;
    }

    Expense makeExpense(){
        Expense expense = new Expense();
        expense.setExpenseId(0);
        expense.setBudgetId(4);
        expense.setCategoryId(2);
        expense.setDate(LocalDate.of(2021,6,17));
        expense.setAmount(BigDecimal.valueOf(10).setScale(2,RoundingMode.HALF_UP));
        expense.setRecurring(true);
        expense.setRecurringTimeframe(RecurringTimeframe.DAILY);

        return expense;
    }
}