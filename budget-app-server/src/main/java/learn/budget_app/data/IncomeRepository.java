package learn.budget_app.data;

import learn.budget_app.models.Income;

import java.util.List;

public interface IncomeRepository {

    List<Income> findAll();

    Income findById(int incomeId);

    Income add(Income income);

    boolean update(Income income);

    boolean deleteById(int incomeId);
}