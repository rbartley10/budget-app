drop database if exists capstone_budget_test;
create database capstone_budget_test;
use capstone_budget_test;

-- create tables and relationships
create table role(
	role_id int primary key auto_increment,
    role_name varchar(250) not null
);

create table user(
	user_id int primary key auto_increment,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    username varchar(250) not null,
    password varchar(250) not null,
    balance double default 0,
    role_id int,
    disabled boolean DEFAULT false,
    
    constraint fk_role_id
		 foreign key(role_id)
        references role(role_id)
  
);

create table income(
	income_id int primary key auto_increment,
    user_id int,
    amount double,
    date date,
    recurring_timeframe varchar(25),
    
    constraint fk_user_id
		 foreign key (user_id)
        references user(user_id)
);

create table category(
	category_id int primary key auto_increment,
    category_name varchar(250) not null
);

create table budget(
	budget_id int primary key auto_increment,
    user_id int,
    goal varchar(250),
    allow_advisor boolean,
    
    constraint fk_budget_user_id
		 foreign key(user_id)
        references user(user_id)
);

create table expense(
	expense_id int primary key auto_increment,
    budget_id int ,
    category_id int,
    date date not null,
    amount double not null,
    recurring boolean,
    recurring_timeframe varchar(25),
    constraint fk_category_id
        foreign key (category_id)
        references category(category_id),
        
	 constraint fk_budget_id
        foreign key (budget_id)
        references budget(budget_id)
);





delimiter //
create procedure set_known_good_state()
begin
    -- Remove all current data from the test database in the correct order to avoid foreign key constraints
    DELETE FROM expense;
    ALTER TABLE expense AUTO_INCREMENT = 1;

    DELETE FROM income;
    ALTER TABLE income AUTO_INCREMENT = 1;

    DELETE FROM budget;
    ALTER TABLE budget AUTO_INCREMENT = 1;

    DELETE FROM user;
    ALTER TABLE user AUTO_INCREMENT = 1;
    
	DELETE FROM role;
    ALTER TABLE role AUTO_INCREMENT = 1;

    DELETE FROM category;
    ALTER TABLE category AUTO_INCREMENT = 1;
    

    -- Reset data in the tables
    INSERT INTO role(role_id, role_name) VALUES
        (1, 'Admin'),
        (2, 'Financial Advisor'),
        (3, 'User');
        
    INSERT INTO user(user_id, first_name, last_name, username, password, balance, role_id) VALUES
        (1, 'Test', 'Admin', 'testAdmin', 'test', 0.0, 1),
        (2, 'Test', 'Advisor', 'testAdvisor', 'test', 0.0, 2),
        (3, 'Test', 'User', 'testUser', 'test', 0.0, 3);
    
    INSERT INTO income(income_id, user_id, amount, date, recurring_timeframe) VALUES
        (1, 2, 10000, '2020-04-22', 'MONTHLY'),
        (2, 3, 3000, '2020-04-22', 'MONTHLY'),
        (3, 3, 50, '2020-04-22', 'WEEKLY');
    
    INSERT INTO category(category_id, category_name) VALUES
        (1, 'Entertainment'),
        (2, 'Food'),
        (3, 'Car'),
        (4, 'Misc'),
        (5, 'Test Category');
        
    INSERT INTO budget(budget_id, user_id, goal, allow_advisor) VALUES
        (1, 3, 'To save for the future', true),
        (2, 3, 'Test description', false),
        (3, 3, 'To save for my children', true),
        (4, 2, 'To save for the future', true),
        (5, 1, 'Test description', false),
        (6, 2, 'To save for my children', true);
        
    INSERT INTO expense(expense_id, budget_id, category_id, date, amount, recurring, recurring_timeframe) VALUES
        (1, 2, 4, '2020-04-22', 100.00, false, 'NONE'),
        (2, 2, 1, '2020-05-23', 14.99, true, 'MONTHLY'),
        (3, 3, 2, '2020-04-22', 25.00, true, 'WEEKLY');
    
end //
delimiter ;

