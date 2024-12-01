# DETAILED PLAN

## Package/Class Overview

```
src
├───main
│   ├───java
│   |    └───learn
│   |        └───budget_app
│   |            │   App.java
│   |            │
│   |            ├───controllers
│   |            │       BudgetController.java
│   |            │       ErrorResponse.java
│   |            │       ExpenseController.java
|   |            |       ExpenseCategoryController.java
|   |            |       GlobalExceptionHandler.java
|   |            |       IncomeController.java
|   |            |       UserController.java
|   |            |
│   |            ├───data
|   |            |       BudgetJdbcTemplateRepository.java
│   |            │       BudgetRepository.java
│   |            │       DataException.java
|   |            |       ExpenseCategoryJdbcTemplateRepository.java
│   |            │       ExpenseCategoryRepository.java
|   |            |       ExpenseJdbcTemplateRepository.java
│   |            │       ExpenseRepository.java
|   |            |       IncomeJdbcTemplateRepository.java
│   |            │       IncomeRepository.java
│   |            │       UserJdbcTemplateRepository.java
│   |            │       UserRepository.java
│   |            │
│   |            ├───domain
│   |            │       BudgetService.java
│   |            │       ExpenseCategoryService.java
│   |            │       ExpenseService.java
│   |            │       IncomeService.java
│   |            │       Result.java
│   |            │       ResultType.java
│   |            │       UserService.java
│   |            │
│   |            └───models
│   |                    Budget.java
│   |                    Expense.java
|   |                    ExpenseCategory.java
|   |                    Income.java
|   |                    Role.java
|   |                    User.java
│   |
|   └───resources
|           application.properties
|
└───test
    ├───java
    │    └───learn
    │        └───budget_app
    │            |
    │            ├───data
    │            |   |   BudgetJdbcTemplateRepositoryTest.java
    │            │   |   BudgetRepositoryTest.java
    │            |   |   ExpenseCategoryJdbcTemplateRepositoryTest.java
    │            │   |   ExpenseCategoryRepositoryTest.java
    │            |   |   ExpenseJdbcTemplateRepositoryTest.java
    │            │   |   ExpenseRepositoryTest.java
    │            |   |   IncomeJdbcTemplateRepositoryTest.java
    │            │   |   IncomeRepositoryTest.java
    |            |   |   Period.java
    │            │   |   KnownGoodState.java
    │            │   |   UserJdbcTemplateRepositoryTest.java
    │            │   |   UserRepositoryTest.java
    │            │   |
    │            │   └───mappers
    │            │          BudgetMapper.java
    │            │          ExpenseCategoryMapper.java
    │            │          ExpenseMapper.java
    │            │          IncomeMapper.java
    │            │          RoleMapper.java
    │            │          UserMapper.java
    │            │
    │            └───domain
    │                    BudgetServiceTest.java
    │                    ExpenseCategoryServiceTest.java
    │                    ExpenseServiceTest.java
    │                    IncomeServiceTest.java
    │                    ResultTest.java
    │                    ResultTypeTest.java
    │                    UserServiceTest.java
    │
    └───resources
            application.properties
```

## Class Details

### App

- `public static void main(String[])`

### data.BudgetJdbcTemplateRepository

- `private int id`
- `private String firstName`
- `private String lastName`
- `private String userName`
- `private String password`
- `private int roleId`
- `private final JdbcTemplate jdbcTemplate`
- `public List<Budget> findAll()`
- `public Budget findById(int)`
- `public Budget add(Budget)`
- `public boolean update(Budget)`
- `public boolean deleteById(int)`

### data.BudgetRepository (interface)

- `List<Budget> findAll()`
- `Budget findById(int)`
- `Budget add(Budget)`
- `boolean update(Budget)`
- `boolean deleteById(int)`

### data.ExpenseCategoryJdbcTemplateRepository

- `private int id`
- `private int userId`
- `private String name`
- `private String description`
- `private final JdbcTemplate jdbcTemplate`
- `public List<ExpenseCategory> findAll()`
- `public ExpenseCategory findById(int)`
- `public ExpenseCategory add(ExpenseCategory)`
- `public boolean update(ExpenseCategory)`
- `public boolean deleteById(int)`

### data.ExpenseCategoryRepository (interface)

- `List<ExpenseCategory> findAll()`
- `ExpenseCategory findById(int)`
- `ExpenseCategory add(ExpenseCategory)`
- `boolean update(ExpenseCategory)`
- `boolean deleteById(int)`

### data.ExpenseJdbcTemplateRepository

- `private int id`
- `private int budgetId`
- `private int categoryId`
- `private String expenseDescription`
- `private final JdbcTemplate jdbcTemplate`
- `public List<Expense> findAll()`
- `public Expense findById(int)`
- `public Expense add(Expense)`
- `public boolean update(Expense)`
- `public boolean deleteById(int)`

### data.ExpenseRepository (interface)

- `List<Expense> findAll()`
- `Expense findById(int)`
- `Expense add(Expense)`
- `boolean update(Expense)`
- `boolean deleteById(int)`

### data.IncomeJdbcTemplateRepository

- `private int id`
- `private int userId`
- `private String incomeDescription`
- `private Period period`
- `private LocalDate date`
- `private final JdbcTemplate jdbcTemplate`
- `public List<Income> findAll()`
- `public Income findById(int)`
- `public Income add(Income)`
- `public boolean update(Income)`
- `public boolean deleteById(int)`

### data.Period (enum)

- `DAILY, WEEKLY, MONTHLY`

### data.IncomeRepository (interface)

- `List<Income> findAll()`
- `Income findById(int)`
- `Income add(Income)`
- `boolean update(Income)`
- `boolean deleteById(int)`

### data.UserJdbcTemplateRepository

- `private int id`
- `private String firstName`
- `private String lastName`
- `private String userName`
- `private String password`
- `private int roleId`
- `private final JdbcTemplate jdbcTemplate`
- `public List<User> findAll()`
- `public User findById(int)`
- `public User add(User)`
- `public boolean update(User)`
- `public boolean deleteById(int)`

### data.UserRepository (interface)

- `List<User> findAll()`
- `User findById(int)`
- `User add(User)`
- `boolean update(User)`
- `boolean deleteById(int)`

### domain.BudgetService

- `private BudgetRepository repository`
- `public BudgetService(BudgetRepository)`
- `public List<Budget> findAll()`
- `public Result<Budget> add(Budget)`
- `public Result<Budget> update(Budget)`
- `public Result<Budget> deleteById(int)`
- `private Result<Budget> validate(Budget)`

### domain.ExpenseCategoryService

- `private ExpenseCategoryRepository repository`
- `public ExpenseCategoryService(ExpenseCategoryRepository)`
- `public List<ExpenseCategory> findAll()`
- `public Result<ExpenseCategory> add(ExpenseCategory)`
- `public Result<ExpenseCategory> update(ExpenseCategory)`
- `public Result<ExpenseCategory> deleteById(int)`
- `private Result<ExpenseCategory> validate(ExpenseCategory)`

### domain.ExpenseService

- `private ExpenseRepository repository`
- `public ExpenseService(ExpenseRepository)`
- `public List<Expense> findAll()`
- `public Result<Expense> add(Expense)`
- `public Result<Expense> update(Expense)`
- `public Result<Expense> deleteById(int)`
- `private Result<Expense> validate(Expense)`

### domain.ExpenseService

- `private IncomeRepository repository`
- `public IncomeService(IncomeRepository)`
- `public List<Income> findAll()`
- `public Result<Income> add(Income)`
- `public Result<Income> update(Income)`
- `public Result<Income> deleteById(int)`
- `private Result<Income> validate(Income)`

### domain.UserService

- `private UserRepository repository`
- `public UserService(UserRepository)`
- `public List<User> findAll()`
- `public Result<User> add(User)`
- `public Result<User> update(User)`
- `public Result<User> deleteById(int)`
- `private Result<User> validate(User)`

### controllers.BudgetController

- `private final BudgetService service`
- `public BudgetController(BudgetService)`
- `public List<Budget> findAll()`
- `public Budget findById(@PathVariable int)`
- `public Result<Budget> add(@RequestBody Budget)`
- `public Result<Budget> update(@PathVariable int, @RequestBody Budget)`
- `public Result<Budget> deleteById(@PathVariable int)`

### controllers.ExpenseCategoryController

- `private final ExpenseCategoryService service`
- `public ExpenseCategoryController(ExpenseCategoryService)`
- `public List<ExpenseCategory> findAll()`
- `public ExpenseCategory findById(@PathVariable int)`
- `public Result<ExpenseCategory> add(@RequestBody ExpenseCategory)`
- `public Result<ExpenseCategory> update(@PathVariable int, @RequestBody ExpenseCategory)`
- `public Result<ExpenseCategory> deleteById(@PathVariable int)`

### controllers.ExpenseController

- `private final ExpenseService service`
- `public ExpenseController(ExpenseService)`
- `public List<Expense> findAll()`
- `public Expense findById(@PathVariable int)`
- `public Result<Expense> add(@RequestBody Expense)`
- `public Result<Expense> update(@PathVariable int, @RequestBody Expense)`
- `public Result<Expense> deleteById(@PathVariable int)`

### controllers.IncomeController

- `private final IncomeService service`
- `public IncomeController(IncomeService)`
- `public List<Income> findAll()`
- `public Income findById(@PathVariable int)`
- `public Result<Income> add(@RequestBody Income)`
- `public Result<Income> update(@PathVariable int, @RequestBody Income)`
- `public Result<Income> deleteById(@PathVariable int)`

### controllers.UserController

- `private final UserService service`
- `public UserController(UserService)`
- `public List<User> findAll()`
- `public User findById(@PathVariable int)`
- `public Result<User> add(@RequestBody User)`
- `public Result<User> update(@PathVariable int, @RequestBody User)`
- `public Result<User> deleteById(@PathVariable int)`

## DATABASE

1. Create Production Database Schema

   - Write DDL and DML to create and populate
   - production database.
   - Write DQL to verify data

2. Create Test Database Schema
   - Write DDL and DML to create and populate test database.
   - Write DQL to verify data

## JAVA

3. Create a Role model

4. Create a User model, repository, service, and RestController to provide full HTTP access to users.

   - Find all users
   - Find a users by their ID
   - Add a users
   - Update an existing user
   - Delete an existing user (delete all references to user in each table in the correct order)

     **Domain Rules:**

   - first name required
   - last name required
   - user name required
   - password required
   - user name cannot be a duplicate

5. Create an Income model, repository, service, and RestController to provide full HTTP access to incomes.

   - Find all incomes
   - Find an income by its ID
   - Add an income
   - Update an existing income
   - Delete an existing incomes (delete all references to income in each table in the correct order)

     **Domain Rules:**

   - User ID is required
   - description required
   - date and period required

6. Create a Budget model, repository, service, and RestController to provide full HTTP access to budgets.

   - Find all budgets
   - Find a budget by its ID
   - Add a budget
   - Update an existing budget
   - Delete an existing budget (delete all references to budget in each table in the correct order)

     **Domain Rules:**

   - User ID is required
   - goal is required

7. Create an Expense_Category model, repository, service, and RestController to provide full HTTP access to expense categories.

   - Find all expense categories
   - Find an expense category by its ID
   - Add an expense category
   - Update an existing expense category
   - Delete an existing expense category (delete all references to expense category in each table in the correct order)

     **Domain Rules:**

   - Name is required

8. Create an Expense model, repository, service, and RestController to provide full HTTP access to expenses.

   - Find all expenses
   - Find an expense by its ID
   - Add an expense
   - Update an existing expense
   - Delete an existing expense (delete all references to expense in each table in the correct order)

     **Domain Rules:**

   - Budget ID is required
   - Category ID is required
   - Date is required
   - Amount is required
   - Timeframe is required

## Front End

9. Create React App

10. Install and implement React Router

11. Allow user to login.

12. Conditionally render based on user

13. Implement full CRUD operations for User in React

14. Implement full CRUD operations for Income in React

15. Implement full CRUD operations for Budget in React

16. Implement full CRUD operations for Expense Category in React

17. Implement full CRUD operations for Expense in React

18. Style application

# CHECKLIST

## DATABASE

- [ ] Build A production Schema
- [ ] Check database with select queries
- [ ] Build A test Schema (with a good state)
- [ ] Check database with select queries

## JAVA

- [ ] Create Role model

- [ ] complete User CRUD operations in Java

  - [ ] Create User model
  - [ ] Create User JDBC template repository
  - [ ] Test User JDBC template repository
  - [ ] Create User Service
  - [ ] Test User Service
  - [ ] Create User Rest Controller
  - [ ] Create HTTP requests

- [ ] complete Income CRUD operations in Java

  - [ ] Create Income model
  - [ ] Create Income JDBC template repository
  - [ ] Test Income JDBC template repository
  - [ ] Create Income Service
  - [ ] Test Income Servce
  - [ ] Create Income Rest Controller
  - [ ] Create HTTP requests

- [ ] complete Budget CRUD operations in Java

  - [ ] Create Budget model
  - [ ] Create Budget JDBC template repository
  - [ ] Test Budget JDBC template repository
  - [ ] Create Budget Service
  - [ ] Test Budget Servce
  - [ ] Create Budget Rest Controller
  - [ ] Create HTTP requests

- [ ] complete Expense_Category CRUD operations in Java

  - [ ] Create Expense_Category model
  - [ ] Create Expense_Category JDBC template repository
  - [ ] Test Expense_Category JDBC template repository
  - [ ] Create Expense_Category Service
  - [ ] Test Expense_Category Service
  - [ ] Create Expense_Category Rest Controller
  - [ ] Create HTTP requests

- [ ] complete Expense CRUD operations in Java
  - [ ] Create Expense model
  - [ ] Create Expense JDBC template repository
  - [ ] Test Expense JDBC template repository
  - [ ] Create Expense Service
  - [ ] Test Expense Service
  - [ ] Create Expense Rest Controller
  - [ ] Create HTTP requests

## React

- [ ] complete login and home screen

  - [ ] fetch list of users
  - [ ] Create Login component
  - [ ] Create Home component
  - [ ] Create Navbar component
  - [ ] Create Dashboard component

- [ ] Conditionally render Dashboard/Navbar options depending on user type

- [ ] complete User CRUD operations in React

  - [ ] Create user list components
  - [ ] Create user form components
  - [ ] write onchange method
  - [ ] add code to POST user
  - [ ] add code to PUT user
  - [ ] add code to DELETE user

- [ ] complete Income CRUD operations in React

  - [ ] fetch list of incomes
  - [ ] Create income list components
  - [ ] Create income form components
  - [ ] write onchange method
  - [ ] add code to POST income
  - [ ] add code to PUT income
  - [ ] add code to DELETE income

- [ ] complete Budget CRUD operations in React

  - [ ] fetch list of budgets
  - [ ] Create budget list components
  - [ ] Create budget form components
  - [ ] write onchange method
  - [ ] add code to POST budget
  - [ ] add code to PUT budget
  - [ ] add code to DELETE budget

- [ ] complete Expense Category CRUD operations in React

  - [ ] fetch list of expense categories
  - [ ] Create expense category list components
  - [ ] Create expense category form components
  - [ ] write onchange method
  - [ ] add code to POST expense category
  - [ ] add code to PUT expense category
  - [ ] add code to DELETE expense category

- [ ] complete Expense CRUD operations in React

  - [ ] fetch list of expenses
  - [ ] Create expense list components
  - [ ] Create expense form components
  - [ ] write onchange method
  - [ ] add code to POST expense
  - [ ] add code to PUT expense
  - [ ] add code to DELETE expense

- [ ] style with boostrap and css
