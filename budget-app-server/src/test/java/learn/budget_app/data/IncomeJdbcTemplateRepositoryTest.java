package learn.budget_app.data;

import learn.budget_app.models.Income;
import learn.budget_app.models.RecurringTimeframe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IncomeJdbcTemplateRepositoryTest {

    @Autowired
    KnownGoodState knownGoodState;

    @Autowired
    IncomeRepository incomeRepository;

    @BeforeEach
    void setup() { knownGoodState.set(); }

    @Test
    void shouldFindAll() {
        List<Income> incomes = incomeRepository.findAll();

        assertTrue(incomes.size() >= 3 && incomes.size() <= 4);
    }

    @Test
    void shouldFindById() {
        Income income = incomeRepository.findById(1);
        BigDecimal amount = new BigDecimal(10000);

        assertEquals(1, income.getIncomeId());
        assertEquals(amount.setScale(2, BigDecimal.ROUND_HALF_UP), income.getAmount());
        assertEquals(RecurringTimeframe.valueOf("MONTHLY"), income.getRecurringTimeframe());
    }

    @Test
    void shouldNotFindById() {
        Income income = incomeRepository.findById(5);

        assertNull(income);
    }

    @Test
    void shouldAddIncome() {
        LocalDate date = LocalDate.of(2024, 9, 23);
        Income income = new Income();
        income.setIncomeId(4);
        income.setUserId(1);
        income.setAmount(new BigDecimal(2000));
        income.setDate(date);
        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));

        Income expected = incomeRepository.add(income);
        assertNotNull(expected);
        assertEquals(LocalDate.of(2024, 9, 23), expected.getDate());
    }

    @Test
    void shouldUpdateIncome() {
        LocalDate date = LocalDate.of(2024, 9, 23);
        Income income = new Income();
        income.setIncomeId(3);
        income.setUserId(1);
        income.setAmount(new BigDecimal(2000));
        income.setDate(date);
        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));

        boolean success = incomeRepository.update(income);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateIncome() {
        LocalDate date = LocalDate.of(2024, 9, 23);
        Income income = new Income();
        income.setIncomeId(5);
        income.setUserId(1);
        income.setAmount(new BigDecimal(2000));
        income.setDate(date);
        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));

        boolean success = incomeRepository.update(income);
        assertFalse(success);
    }
}
