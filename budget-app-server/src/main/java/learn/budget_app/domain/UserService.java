package learn.budget_app.domain;

import learn.budget_app.data.UserRepository;
import learn.budget_app.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public User findByUsername(String username) { return userRepository.findByUsername(username); }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getUserId() != 0) {
            result.addMessage("User Id must not be set", ResultType.INVALID);
            return result;
        }
        userRepository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> update(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (!userRepository.update(user)) {
            result.addMessage(String.format("Could not find user with ID: %s", user.getUserId()),

                    ResultType.NOT_FOUND);
            return result;
        }
        return result;
    }

    public boolean deleteById(int userId) {
        return userRepository.deleteById(userId);
    }

    // helpers

    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        List<User> users = this.userRepository.findAll();

        if (user == null) {
            result.addMessage("User cannot be null", ResultType.INVALID);
            return result;
        }
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            result.addMessage("Username is required", ResultType.INVALID);
        }

        if (user.getUserName().length() < 4 || user.getUserName().length() > 15) {
            result.addMessage("Username must be between 4 and 15 characters", ResultType.INVALID);
        }
        for (User i : users) {
            if (Objects.equals(i.getUserName(), user.getUserName())) {
                result.addMessage("Username must be unique", ResultType.INVALID);
                break;
            }
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            result.addMessage("First name is required", ResultType.INVALID);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            result.addMessage("Last name is required", ResultType.INVALID);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.addMessage("Password is required", ResultType.INVALID);
        }

        if (user.getPassword().length() < 8 || user.getPassword() == null) {
            result.addMessage("Password must be greater than 8", ResultType.INVALID);
        }
        return result;
    }
}
