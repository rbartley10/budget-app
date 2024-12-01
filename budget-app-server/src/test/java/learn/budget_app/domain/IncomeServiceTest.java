package learn.budget_app.domain;

import learn.budget_app.data.IncomeRepository;
import learn.budget_app.models.Income;
import learn.budget_app.models.RecurringTimeframe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IncomeServiceTest {

    @Autowired
    IncomeService incomeService;

    @MockBean
    IncomeRepository incomeRepository;

//    @Test
//    void shouldAdd() {
//        Income income = new Income();
//        income.setIncomeId(0);
//        income.setUserId(1);
//        income.setAmount(new BigDecimal(2000));
//        income.setDate(LocalDate.now());
//        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));
//
//        Income actual = new Income();
//        actual.setIncomeId(4);
//        actual.setUserId(1);
//        actual.setAmount(new BigDecimal(2000));
//        actual.setDate(LocalDate.now());
//        actual.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));
//
//        when(incomeRepository.add(income)).thenReturn(actual);
//        Result<Income> result = incomeService.add(income);
//        assertEquals(result.getType(), ResultType.SUCCESS);
//    }
//
//    @Test
//    void shouldNotAddInvalid() {
//        Income income = new Income();
//
//        // bad id
//        income.setIncomeId(1);
//        income.setUserId(1);
//        income.setAmount(new BigDecimal(2000));
//        income.setDate(LocalDate.now());
//        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));
//        Result<Income> result = incomeService.add(income);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);
//
//        // bad date
//        income.setIncomeId(0);
//        income.setDate(LocalDate.of(2020, 1, 1));
//        result = incomeService.add(income);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);
//
//        // bad amount
//        income.setDate(LocalDate.now());
//        income.setAmount(new BigDecimal(-1000));
//        result = incomeService.add(income);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);
//    }
//
//    @Test
//    void shouldUpdate() {
//        Income income = new Income();
//        income.setIncomeId(3);
//        income.setUserId(1);
//        income.setAmount(new BigDecimal(2000));
//        income.setDate(LocalDate.now());
//        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));
//
//        when(incomeRepository.update(income)).thenReturn(true);
//        Result<Income> result = incomeService.update(income);
//        assertEquals(result.getType(), ResultType.SUCCESS);
//    }
//
//    @Test
//    void shouldNotUpdateNotFound() {
//        Income income = new Income();
//        income.setIncomeId(5);
//        income.setUserId(1);
//        income.setAmount(new BigDecimal(2000));
//        income.setDate(LocalDate.now());
//        income.setRecurringTimeframe(RecurringTimeframe.valueOf("BIWEEKLY"));
//
//        when(incomeRepository.update(income)).thenReturn(false);
//        Result<Income> result = incomeService.update(income);
//        assertEquals(result.getType(), ResultType.NOT_FOUND);
//        assertEquals(result.getMessages().size(), 1);
//    }
//
//    @Test
//    void shouldNotUpdateInvalid() {
//        Income income = new Income();
//        income.setIncomeId(0);
//        income.setUserId(1);
//        income.setAmount(new BigDecimal(2000));
//
//        // bad date
//        income.setDate(LocalDate.of(2020, 1, 1));
//        Result<Income> result = incomeService.update(income);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);
//
//        // bad amount
//        income.setDate(LocalDate.now());
//        income.setAmount(new BigDecimal(-1000));
//        result = incomeService.update(income);
//        assertEquals(result.getType(), ResultType.INVALID);
//        assertEquals(result.getMessages().size(), 1);
//    }
}
