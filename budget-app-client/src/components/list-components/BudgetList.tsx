import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/BudgetList.css';

type Budget = {
  budgetId: number;
  userId: number;
  goal: string;
  allowAdvisor: boolean;
};



function BudgetList({ userId }: { userId: string }) {
  const [budgets, setBudgets] = useState([] as Budget[]);
  // const [user, setUser] = useState();

  const handleDelete = (budgetId: number) => {
    const budget: Budget = budgets.find((b) => b.budgetId === budgetId)!;
    if (
      window.confirm(
        `Delete Budget: ${budget.goal} with ID: ${budget.budgetId}?`
      )
    ) {
      const init = {
        method: 'DELETE',
      };
      fetch(`${url}/${budgetId}`, init)
        .then((response) => {
          if (response.status === 204) {
            const newBudgets = budgets.filter((b) => b.budgetId !== budgetId);
            setBudgets(newBudgets);
          } else {
            return Promise.reject(`Unexpected Status Code ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const url = 'http://localhost:8080/api/budget';

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => setBudgets(data))
      .catch(console.log);
  });

  return (
    <>
      <section className="container">
        <h3>Budget List</h3>
        <Link to={`/budget-form-add/${userId}`}  className="btn btn-info">Add Budget</Link>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Goal</th>
              <th>Allow Advisor</th>
              <th>&nbsp;</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {budgets
              .filter((b) => b.userId === Number(userId))
              .map((budget) => (
                <tr key={budget.budgetId}>
                  <td>{budget.budgetId}</td>
                  <td>{budget.goal}</td>
                  <td>{budget.allowAdvisor ? 'yes' : 'no'}</td>
                  <td>
                    <button className="btn btn-warning">Edit</button>
                    <button
                      className="btn btn-danger m-1"
                      onClick={() => handleDelete(budget.budgetId)}
                    >
                      Delete
                    </button>
                  </td>
                  <td>
                    <Link to={`/expenses/${budget.budgetId}`} className="btn btn-info">
                      View
                    </Link>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </section>
    </>
  );
}

export default BudgetList;
