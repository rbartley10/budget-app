package learn.budget_app.data;

import learn.budget_app.models.Budget;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BudgetRepository {
    List<Budget> findAll();

    @Transactional
    Budget findById(int budgetId);

    Budget add(Budget budget);

    boolean update(Budget budget);

    @Transactional
    boolean deleteById(int budgetId);
}
