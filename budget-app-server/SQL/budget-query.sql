use capstone_budget_test;

SET SQL_SAFE_UPDATES = 0;
CALL set_known_good_state();

-- Select alls
Select * from budget;
Select * from category;
Select * from expense;
Select * from income;
Select * from role;
Select * from user;

 -- Filters
 
-- Returns all expense for a user
  Select c.category_name, e.recurring_timeframe, e.amount from expense e
 inner join category c on c.category_id = e.category_id
 inner join budget b on e.budget_id = b.budget_id
 inner join user u on b.user_id = u.user_id
 where  u.user_id = 3;
 
  -- All Expense that are monthly for a particular user( Maybe for the advisor or user to look at their overall information
 Select c.category_name, e.recurring_timeframe, e.amount from expense e
 inner join category c on c.category_id = e.category_id
 inner join budget b on e.budget_id = b.budget_id
 inner join user u on b.user_id = u.user_id
 where recurring_timeframe = 'MONTHLY' and u.user_id = 3;
 
 
  -- All Expense that are monthly for a particular user and of a specfic category( Maybe for the advisor or user to look at their overall information
  Select c.category_name, e.recurring_timeframe, e.amount from expense e
 inner join category c on c.category_id = e.category_id
 inner join budget b on e.budget_id = b.budget_id
 inner join user u on b.user_id = u.user_id
 where recurring_timeframe = 'MONTHLY' and u.user_id = 3 and c.category_name = 'Entertainment';
 
DELETE ti
FROM ticket_info ti
INNER JOIN (
    SELECT ti.customer_id
    FROM ticket_info ti
    INNER JOIN customer c ON c.customer_id = ti.customer_id
    INNER JOIN performance p ON ti.show_id = p.performance_id
    GROUP BY ti.customer_id
    HAVING COUNT(ti.customer_id) = 1
       AND MIN(p.theatre_id) = 1
) AS subquery ON ti.customer_id = subquery.customer_id;
 
 Delete from income where user_id = 3;
DELETE FROM budget
WHERE budget_id IN (
    SELECT * FROM (
        SELECT DISTINCT bu.budget_id
        FROM budget bu
        INNER JOIN user u ON bu.user_id = u.user_id
        WHERE u.user_id = 3
    ) AS subquery
);
 