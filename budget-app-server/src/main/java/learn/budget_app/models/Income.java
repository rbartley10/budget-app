package learn.budget_app.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;

public class Income {

    private int incomeId;
    private int userId;
    private BigDecimal amount;
    private LocalDate date;
    private RecurringTimeframe recurringTimeframe;

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public RecurringTimeframe getRecurringTimeframe() {
        return recurringTimeframe;
    }

    public void setRecurringTimeframe(RecurringTimeframe recurringTimeframe) {
        this.recurringTimeframe = recurringTimeframe;
    }
}