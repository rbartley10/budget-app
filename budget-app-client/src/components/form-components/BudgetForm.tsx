
import React, { FormEventHandler } from 'react';
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import '../../styles/BudgetForm.css';

type UserDetails = Record<string, string>;

type Budget = {
  budgetId: number;
  userId: number;
  goal: string;
  allowAdvisor: boolean;
};

function BudgetForm() {

  
  const [goal, setGoal] = useState('');
  const [budget, setBudget] = useState<Budget>();
  const [errors, setErrors] = useState([]);
  const url = 'http://localhost:8080/api/budget';
  const { userId } = useParams<UserDetails>(); // Extract userId from URL
  const navigate = useNavigate();
  
  console.log(userId);

  const handleChange = (event: any) => {
    const newBudget: any = { ...budget };
    newBudget[event.target.name] = event.target.value;
    setBudget(newBudget);
  };

  const handleSubmit = (event: { preventDefault: () => void }) => {
    
    const numericUserId = Number(userId);
    console.log(numericUserId);
    budget!.userId = numericUserId
    budget!.allowAdvisor = true
    event.preventDefault();   
    add(budget!); 
    navigate(`/user/${userId}`);
  };

  const add = (budget: Budget) => {
    console.log(budget)
    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(budget),
    };
    fetch(url, init)
      .then((response) => {
        if (response.status === 200 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data.budgetId) {
          // navigate(`user/${data.userId}`); // go back to the user dahsboard
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };

  return (
    <section id="budgetForm">
      <h3>Add a Budget to USER ID: {userId}</h3>
      <form onSubmit={handleSubmit}>

        <fieldset className="form-group">
        <input
            type="number"
            id="userId"
            name="userId"
            value= {userId}
            className="form-control"
            hidden
          />
          <input
            type="text"
            id="goal"
            name="goal"
            placeholder="ENTER A GOAL DESCRITPTION"
            className="form-control"
            onChange={handleChange}
          />
          
          <input
            type="checkbox"
            id="allowAdvisor"
            name="allowAdvisor"
            className="form-control"
            onChange={handleChange}
            hidden
          />
        </fieldset>
       
        <button type="submit" className="btn btn-outline-success">
          Add Budget
        </button>
        <Link to={`/user/${userId}`} type="button" className="btn btn-outline-danger">
          Cancel
        </Link>
      </form>
    </section>
  );
}

export default BudgetForm;
