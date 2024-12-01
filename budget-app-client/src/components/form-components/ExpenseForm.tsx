
import React, { FormEventHandler } from 'react';
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import '../../styles/ExpenseForm.css';


type BudgetDetails = Record<string, string>;

type Category = {
  categoryId: number; 
  categoryName: string; 
};

interface Expense {
  expenseId: number;
  budgetId: number;
  categoryId: number;
  date: string | null; // Adjust based on your needs
  amount: number;
  recurring: boolean;
  recurringTimeframe: string | null;
}


const DEFAULT_EXPENSE = {
  expenseId: 0,
  budgetId: 0,
  categoryId: 0,
  date: null,
  amount: 0,
  recurring: false,
  recurringTimeframe: null,
};

const DEFAULT_CATEGORY = {
  categoryId: 0,
  categoryName: '',
};

function ExpenseForm() {
  const [expense, setExpense] = useState(DEFAULT_EXPENSE);
  const [categories, setCategories] = useState<Category[]>([]); // Type categories as an array of Category
  const [errors, setErrors] = useState([]);
  const { budgetId, expenseId } = useParams<BudgetDetails>(); // Extract userId from URL
  const navigate = useNavigate();

  useEffect(() => {
    if (expenseId) {
      fetch(`http://localhost:8080/api/expense/${expenseId}`)
        .then((response) => {
          if (response.status === 200) {
            return response.json();
          } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
          }
        })
        .then((data) => {
          setExpense(data);
        })
        .catch(console.log);
    } else {
      setExpense(DEFAULT_EXPENSE);
    }
  }, [expenseId, budgetId]);

  useEffect(() => {
    fetch('http://localhost:8080/api/category')  
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
       
        setCategories(data); 
      })
      .catch((error) => {
        console.error('Error fetching categories:', error);
      });
  }, []); 

  const postExpense = (expense: object) => {
    console.log(expense);
    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(expense),
    };
    fetch('http://localhost:8080/api/expense', init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          console.log(response.status)
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data.budgetId) {
          console.log("It Worked!!!")
          navigate(`/expenses/${data.budgetId}`); //goback to budget dashboard
        } 
        else {
          setErrors(data);
          console.log(errors);
        }
      })
      .catch(console.log);
  };

  

  const putExpense = (expense: Expense) => { 
    expense.expenseId = Number(expenseId);
     expense.budgetId = Number(budgetId);
    console.log(expense);
    const init = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(expense),
    };
    fetch(`http://localhost:8080/api/expense/${expenseId}`, init)
      .then((response) => {
        if (response.status === 204) {
          navigate(`/expenses/${budgetId}`)
          return null;
        } else if (response.status === 400 || response.status === 409) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data) {
          setErrors(data);
          console.log(errors);
        }
      })
      .catch(console.log);
  };

  const handleChange = (event: any) => {
    const newExpense: any = { ...expense };
    newExpense[event.target.name] = event.target.value;
    setExpense(newExpense);
  };

  const handleClear = () => {
    setExpense(DEFAULT_EXPENSE);
  };

  const handleSubmit = (event: any) => {
    // const numericBudgetId = Number(budgetId);
    // expense!.budgetId = numericBudgetId
    
    event.preventDefault();
    const numericBudgetId = Number(budgetId);
    if (isNaN(numericBudgetId)) {
    console.error('Invalid budgetId:', budgetId); // Log if it's invalid
    return; // Optionally handle the error
}
    expense.budgetId = numericBudgetId;
    expense!.recurring = true
    if (expenseId) {
      console.log("Hitting PUt Method");
      putExpense(expense);
    } else {
      postExpense(expense);
    }

    
  };

  return (
    <section id="expenseForm">
      <h3>Add an Expense</h3>
      <form onSubmit={handleSubmit}>
        <fieldset className="form-group">
          <input
            type="number"
            id="amount"
            name="amount"
            placeholder="Amount"
            value = {expense.amount}
            className="form-control"
            onChange={handleChange}
          />
          
          <input
            type="date"
            id="date"
            name="date"
            placeholder="Date"
            value = {expense.date || ""}
            className="form-control"
            onChange={handleChange}
          />
        </fieldset>
        <fieldset className="form-group">
          <input
            type="checkbox"
            id="recurring"
            name="recurring"
            className="form-control"
            onChange={handleChange}
            hidden
          />
          
          <select
            name="recurringTimeframe"
            id="recurringTimeframe"
            defaultValue={'Cycle'}
            value = {expense.recurringTimeframe || ""} 
            className="form-control"
            onChange={handleChange}
          >
            <option value={'DAILY'}>Daily</option>
            <option value={'WEEKLY'}>Weekly</option>
            <option value={'MONTHLY'}>Monthly</option>
            <option value={'YEARLY'}>Yearly</option>
          </select>
        </fieldset>
        <fieldset className="form-group">
        <select
          name="categoryId"
          id="category"
          value={expense.categoryId}
          className="form-control"
          onChange={handleChange}
        >
          <option value={0}>Select Category</option>
          {categories.map((c) => (
            <option key={c.categoryId} value={c.categoryId}>
              {c.categoryName}
            </option>
          ))}
        </select>
        </fieldset>
        <button type="submit" className="btn btn-outline-success">
          {expense.expenseId > 0 ? "Update Expense" : "Add Expense"}
        </button>
        <button type="button" className="btn btn-outline-danger"  onClick={handleClear}>
          Clear Form
        </button>
      </form>
    </section>
  );
}

export default ExpenseForm;
