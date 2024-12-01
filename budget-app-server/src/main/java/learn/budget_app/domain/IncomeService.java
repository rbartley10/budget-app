package learn.budget_app.domain;

import learn.budget_app.data.IncomeRepository;
import learn.budget_app.models.Income;
import learn.budget_app.models.RecurringTimeframe;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public List<Income> findAll() {
        return incomeRepository.findAll();
    }

    public Income findById(int incomeId) {
        return incomeRepository.findById(incomeId);
    }

    public Result<Income> add(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }
        if (income.getIncomeId() != 0) {
            result.addMessage("Income id should not be set", ResultType.INVALID);
            return result;
        }
        incomeRepository.add(income);
        result.setPayload(income);
        return result;
    }

    public Result<Income> update(Income income) {
        Result<Income> result = validate(income);
        if (!result.isSuccess()) {
            return result;
        }
        if (!incomeRepository.update(income)) {
            result.addMessage(String.format("Could not find Income ID: %s", income.getIncomeId()),
                    ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }

    public boolean deleteById(int incomeId) {
        return incomeRepository.deleteById(incomeId);
    }

    // helpers
    private Result<Income> validate(Income income) {
        Result<Income> result = new Result<>();
        if (income == null) {
            result.addMessage("Income cannot be null", ResultType.INVALID);
            return result;
        }
        if (income.getAmount().doubleValue() < 0) {
            result.addMessage("Income cannot be negative", ResultType.INVALID);
        }
        if (income.getAmount() == null) {
            result.addMessage("Amount is required", ResultType.INVALID);
        }
        if (income.getDate().isBefore(LocalDate.of(LocalDate.now().getYear() - 1,
                LocalDate.now().getMonth().minus(1), LocalDate.now().getDayOfMonth() - 1))) {
            result.addMessage("Last payday cannot be more than a year ago", ResultType.INVALID);
        }
        return result;
    }
}
