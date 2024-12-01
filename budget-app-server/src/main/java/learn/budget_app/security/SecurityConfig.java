package learn.budget_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // 1 The GET->POST CSRF mechanism doesn't apply to REST APIs.

        http.authorizeRequests() // 2 It's necessary here to recreate the original USER and ADMIN permissions.
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/refresh_token").authenticated()


                //ROLES
                // Admin
                // Financial Advisor - (CAN UPDATE)
                // User - genericUser can add/update the following (INCOMES, EXPENSES, BUDGETS)


                //Everyone can get information on their accounts(INCOME, EXPENSES, BUDGET)
                //FOR ADVISORS (THEY SHOULD BE ABLE TO SEE ONLY INFORMATION FOR BUDGETS THAT THEY HAVE SPECIFIC APPROVAL TO LOOK AT
                //FOR ADMINS(THEY SHOULD BE ABLE TO SEE A LIST OF ALL THE USERS)
                .antMatchers(HttpMethod.GET, "/budget/", "/budget/*").permitAll()

                //Users on their accounts should be able to post NEW information... but not advisors
                .antMatchers(HttpMethod.POST, "/budget/*").permitAll()

                //USERS should be able to update information on their accounts such as income, budgets, expenses
                .antMatchers(HttpMethod.PUT, "/budget/income/*", "/budget/expense/*").permitAll()

                //USERS and Advisors should be able to delete expenses off their report ONLY ADMINS CAN DELETE AN ACCOUNT
                .antMatchers(HttpMethod.DELETE, "/budget/income/*", "/budget/expense/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/budget/*").permitAll()
                .and()

                .addFilter(new JwtRequestFilter(authenticationManager(), converter)) // 3 The filter intercepts the HTTP request early and determines authentication and authorization. The antMatchers apply their rules after the filter is complete.
                .sessionManagement() // 4
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

