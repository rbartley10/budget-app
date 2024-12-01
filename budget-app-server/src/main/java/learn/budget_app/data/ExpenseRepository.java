package learn.budget_app.data;

import learn.budget_app.models.Expense;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpenseRepository {
    List<Expense> findAll();

    @Transactional
    Expense findById(int expenseId);

    Expense add(Expense expense);

    boolean update(Expense expense);

    @Transactional
    boolean deleteById(int expenseId);
}
