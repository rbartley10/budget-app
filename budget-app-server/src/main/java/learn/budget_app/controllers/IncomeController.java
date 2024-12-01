package learn.budget_app.controllers;

import learn.budget_app.domain.IncomeService;
import learn.budget_app.domain.Result;
import learn.budget_app.models.Income;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/income")
public class IncomeController {

    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Income> findAll() {
        return service.findAll();
    }

    @GetMapping("/{incomeId}")
    public Income findById(@PathVariable int incomeId) {
        return service.findById(incomeId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Income income) {
        Result<Income> result = service.add(income);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<Object> update(@PathVariable int incomeId, @RequestBody Income income) {
        if (incomeId != income.getIncomeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Income> result = service.update(income);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Object> deleteById(@PathVariable int incomeId) {
        if (service.deleteById(incomeId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
