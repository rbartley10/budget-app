package learn.budget_app.domain;

import learn.budget_app.data.ExpenseRepository;
import learn.budget_app.models.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> findAll(){
        return repository.findAll();
    }

    public Expense findById(int expenseId){
        return repository.findById(expenseId);
    }

    public Result<Expense> add(Expense expense){
        Result<Expense> result = validate(expense);
        if (!result.isSuccess()) {
            return result;
        }

        if (expense.getExpenseId() != 0) {
            result.addMessage("expenseId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        expense = repository.add(expense);
        result.setPayload(expense);
        return result;
    }

    public Result<Expense> update(Expense expense){
        Result<Expense> result = validate(expense);
        if (!result.isSuccess()) {
            return result;
        }

        if (expense.getExpenseId() <= 0) {
            result.addMessage("expense id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(expense)) {
            String msg = String.format("expense with id %s, not found", expense.getExpenseId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int expenseId){
        return repository.deleteById(expenseId);
    }

    private Result<Expense> validate(Expense expense){
        Result<Expense> result = new Result<>();
        if(expense == null){
            result.addMessage("expense cannot be null", ResultType.INVALID);
            return result;
        }

        if(expense.getBudgetId() <= 0){
            result.addMessage("budget id is required", ResultType.INVALID);
        }
        if(expense.getCategoryId() <= 0){
            result.addMessage("category id is required", ResultType.INVALID);
        }
        if(expense.getDate() == null){
            result.addMessage("date is required", ResultType.INVALID);
        }
        if(expense.getAmount() == null){
            result.addMessage("amount is required", ResultType.INVALID);
        }
        if(expense.getRecurringTimeframe() == null){
            result.addMessage("timeframe is required", ResultType.INVALID);
        }
        return result;

    }
}
