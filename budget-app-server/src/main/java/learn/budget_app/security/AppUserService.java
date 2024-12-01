package learn.budget_app.security;

import learn.budget_app.data.RoleRepository;
import learn.budget_app.data.UserRepository;
import learn.budget_app.domain.Result;
import learn.budget_app.domain.UserService;
import learn.budget_app.models.AppUser;
import learn.budget_app.models.Role;
import learn.budget_app.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService{
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AppUserService(UserService service, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userService = service;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //GET THE USER FROM THE DB
        User user = userService.findByUsername(username);
        Role role = roleRepository.findById(user.getRoleId());

        List<String> roleName = new ArrayList<>();

        roleName.add(role.getRoleName());

        AppUser appUser = new AppUser(user.getUserId(), user.getUserName(), user.getPassword(), user.isDisabled(), roleName);

        if (user == null || user.isDisabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public User create(String firstName, String lastName,String username, String password) {
        validate(username);
        validatePassword(password);

        password = encoder.encode(password);



        //THIS SHOULD MAKE A USER MODEL INSTEAD

        User user = new User(firstName, lastName, username, password, 3);
        user.setBalance(BigDecimal.valueOf(0.0));

        User result = userService.add(user).getPayload();

        System.out.println(result);

        return result;


    }

    private void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("username is required");
        }

        if (username.length() > 50) {
            throw new ValidationException("username must be less than 50 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("password must be at least 8 characters");
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        if (digits == 0 || letters == 0 || others == 0) {
            throw new ValidationException("password must contain a digit, a letter, and a non-digit/non-letter");
        }
    }


}
