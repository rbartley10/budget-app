package learn.budget_app.controllers;

import learn.budget_app.domain.ExpenseService;
import learn.budget_app.domain.Result;
import learn.budget_app.models.Expense;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Expense> findAll() {
        return service.findAll();
    }

    @GetMapping("/{expenseId}")
    public Expense findById(@PathVariable int expenseId) {
        return service.findById(expenseId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Expense expense) {
        Result<Expense> result = service.add(expense);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Object> update(@PathVariable int expenseId, @RequestBody Expense expense) {
        if (expenseId != expense.getExpenseId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Expense> result = service.update(expense);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteById(@PathVariable int expenseId) {
        if (service.deleteById(expenseId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
