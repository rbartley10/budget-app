package learn.budget_app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Budget {
    private int budgetId;
    private int userId;
    private String goal;
    private boolean allowAdvisor;
    private List<Expense> expenses = new ArrayList<>();

    public Budget(){}

    public Budget(int budgetId, int userId, String goal, boolean allowAdvisor) {
        this.budgetId = budgetId;
        this.userId = userId;
        this.goal = goal;
        this.allowAdvisor = allowAdvisor;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isAllowAdvisor() {
        return allowAdvisor;
    }

    public void setAllowAdvisor(boolean allow_advisor) {
        this.allowAdvisor = allow_advisor;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return budgetId == budget.budgetId && userId == budget.userId && allowAdvisor == budget.allowAdvisor && Objects.equals(goal, budget.goal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, userId, goal, allowAdvisor);
    }
}
