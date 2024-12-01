package learn.budget_app.controllers;

import learn.budget_app.domain.BudgetService;
import learn.budget_app.domain.Result;
import learn.budget_app.models.Budget;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @GetMapping
    public List<Budget> findAll() {
        return service.findAll();
    }

    @GetMapping("/{budgetId}")
    public Budget findById(@PathVariable int budgetId) {
        return service.findById(budgetId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Budget budget) {
        Result<Budget> result = service.add(budget);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Object> update(@PathVariable int budgetId, @RequestBody Budget budget) {
        if (budgetId != budget.getBudgetId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Budget> result = service.update(budget);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteById(@PathVariable int budgetId) {
        if (service.deleteById(budgetId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
