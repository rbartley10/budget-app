package learn.budget_app.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Expense {
    private int expenseId;
    private int budgetId;
    private int categoryId;
    private LocalDate date;
    private BigDecimal amount;
    private boolean recurring;
    private RecurringTimeframe recurringTimeframe;

    public Expense(){}

    public Expense(int expenseId, int budgetId, int categoryId, LocalDate date, BigDecimal amount, boolean recurring, RecurringTimeframe recurringTimeframe) {
        this.expenseId = expenseId;
        this.budgetId = budgetId;
        this.categoryId = categoryId;
        this.date = date;
        this.amount = amount;
        this.recurring = recurring;
        this.recurringTimeframe = recurringTimeframe;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public RecurringTimeframe getRecurringTimeframe() {
        return recurringTimeframe;
    }

    public void setRecurringTimeframe(RecurringTimeframe recurringTimeframe) {
        this.recurringTimeframe = recurringTimeframe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return expenseId == expense.expenseId && budgetId == expense.budgetId && categoryId == expense.categoryId && recurring == expense.recurring && Objects.equals(date, expense.date) && Objects.equals(amount, expense.amount) && recurringTimeframe == expense.recurringTimeframe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, budgetId, categoryId, date, amount, recurring, recurringTimeframe);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", budgetId=" + budgetId +
                ", categoryId=" + categoryId +
                ", date=" + date +
                ", amount=" + amount +
                ", recurring=" + recurring +
                ", recurringTimeframe=" + recurringTimeframe +
                '}';
    }
}
