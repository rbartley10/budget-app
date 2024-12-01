package learn.budget_app.domain;

import learn.budget_app.data.BudgetRepository;
import learn.budget_app.models.Budget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository repository;

    public BudgetService(BudgetRepository repository) {
        this.repository = repository;
    }

    public List<Budget> findAll(){
        return repository.findAll();
    }

    public Budget findById(int budgetId){
        return repository.findById(budgetId);
    }

    public Result<Budget> add(Budget budget){
        Result<Budget> result = validate(budget);
        if (!result.isSuccess()) {
            return result;
        }

        if (budget.getBudgetId() != 0) {
            result.addMessage("budgetId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        budget = repository.add(budget);
        result.setPayload(budget);
        return result;
    }

    public Result<Budget> update(Budget budget){
        Result<Budget> result = validate(budget);
        if (!result.isSuccess()) {
            return result;
        }

        if (budget.getBudgetId() <= 0) {
            result.addMessage("budget id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(budget)) {
            String msg = String.format("budget with id %s, not found", budget.getBudgetId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int budgetId){
        return repository.deleteById(budgetId);
    }

    private Result<Budget> validate(Budget budget){
        Result<Budget> result = new Result<>();
        if(budget == null){
            result.addMessage("budget cannot be null", ResultType.INVALID);
            return result;
        }

        if(budget.getUserId() <= 0){
            result.addMessage("user id is required", ResultType.INVALID);
        }
        if(budget.getGoal().isEmpty() || budget.getGoal().isBlank()){
            result.addMessage("goal is required", ResultType.INVALID);
        }

        return result;

    }
}
