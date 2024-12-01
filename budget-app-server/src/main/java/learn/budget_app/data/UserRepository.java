package learn.budget_app.data;

import learn.budget_app.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int userId);

    @Transactional
    User findByUsername(String username);

    User add(User user);

    boolean update(User user);

    boolean deleteById(int userId);
}