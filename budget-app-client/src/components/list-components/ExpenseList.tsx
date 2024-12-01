import { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';

type Expense = {
  expenseId: number;
  budgetId: number;
  categoryId: number;
  date: string;
  amount: number;
  recurring: boolean;
  recurringTimeframe: string;
};

function ExpenseList() {
  const [expenses, setExpenses] = useState([] as Expense[]);
  const { budgetId } = useParams();

  const handleDelete = (expenseId: number) => {
    const expense: Expense = expenses.find((e) => e.expenseId === expenseId)!;
    if (window.confirm(`Delete Expense: with ID ${expense.expenseId}?`)) {
      const init = {
        method: 'DELETE',
      };
      fetch(`${url}/${expenseId}`, init)
        .then((response) => {
          if (response.status === 204) {
            const newExpenses = expenses.filter(
              (e) => e.expenseId !== expenseId
            );
            setExpenses(newExpenses);
          } else {
            return Promise.reject(`Unexpected Status Code ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const url = 'http://localhost:8080/api/expense';

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => setExpenses(data))
      .catch(console.log);
  });

  return (
    <>
      <section className="container">
        <h2>Expense List</h2>
        <Link to={`/expense-form-add/${budgetId}`} className="btn btn-info">
          Add Expense
        </Link>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Budget ID</th>
              <th>Category ID</th>
              <th>Date</th>
              <th>Amount</th>
              <th>Recurring</th>
              <th>Timeframe</th>
              <th>&nbsp;</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {expenses
              .filter((e) => e.budgetId === Number(budgetId))
              .map((expense) => (
                <tr key={expense.expenseId}>
                  <td>{expense.budgetId}</td>
                  <td>{expense.categoryId}</td>
                  <td>{expense.date}</td>
                  <td>{expense.amount}</td>
                  <td>{expense.recurring ? 'yes' : 'no'}</td>
                  <td>{expense.recurringTimeframe}</td>
                  <td>
                  <Link to={`/expenses/edit/${expense.budgetId}/${expense.expenseId}`} className="btn btn-info">
         Edit
        </Link>
                  </td>
                  <td>
                    <button
                      className="btn btn-danger"
                      onClick={() => handleDelete(expense.expenseId)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </section>
    </>
  );
}

export default ExpenseList;
