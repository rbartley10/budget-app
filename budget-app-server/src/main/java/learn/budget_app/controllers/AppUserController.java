package learn.budget_app.controllers;

import learn.budget_app.domain.UserService;
import learn.budget_app.domain.Result;
import learn.budget_app.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/appuser")
public class AppUserController {

    private final UserService service;

    public AppUserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable int userId) {
        return service.findById(userId);
    }

    @GetMapping("/username/{username}")
    public User findByUsername(@PathVariable String username) { return service.findByUsername(username); }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody User user) {
        Result<User> result = service.add(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable int userId, @RequestBody User user) {
        if (userId != user.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<User> result = service.update(user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteById(@PathVariable int userId) {
        if (service.deleteById(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
