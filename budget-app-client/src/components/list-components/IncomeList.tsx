import { useState, useEffect } from 'react';

type Income = {
  incomeId: number;
  userId: number;
  amount: number;
  date: string;
  recurringTimeframe: string;
};

// const INCOMES: Income[] = [
//   {
//     incomeId: 1,
//     userId: 2,
//     amount: 10000,
//     date: '2020-04-22',
//     recurringTimeframe: 'MONTHLY',
//   },
//   {
//     incomeId: 2,
//     userId: 3,
//     amount: 3000,
//     date: '2020-04-22',
//     recurringTimeframe: 'MONTHLY',
//   },
//   {
//     incomeId: 3,
//     userId: 3,
//     amount: 50,
//     date: '2020-04-22',
//     recurringTimeframe: 'WEEKLY',
//   },
// ];

function IncomeList() {
  const [incomes, setIncomes] = useState([] as Income[]);

  const handleDelete = (incomeId: number) => {
    const income: Income = incomes.find((i) => i.incomeId === incomeId)!;
    if (window.confirm(`Delete Income: with ID ${income.incomeId}?`)) {
      const init = {
        method: 'DELETE',
      };
      fetch(`${url}/${incomeId}`, init)
        .then((response) => {
          if (response.status === 204) {
            const newIncomes = incomes.filter((i) => i.incomeId !== incomeId);
            setIncomes(newIncomes);
          } else {
            return Promise.reject(`Unexpected Status Code ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const url = 'http://localhost:8080/api/income';

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => setIncomes(data))
      .catch(console.log);
  });

  return (
    <>
      <section className="container">
        <h2>Income List</h2>
        <button>Add Income</button>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Amount</th>
              <th>Date</th>
              <th>Timeframe</th>
              <th>&nbsp;</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {incomes.map((income) => (
              <tr key={income.incomeId}>
                <td>{income.userId}</td>
                <td>${income.amount}</td>
                <td>{income.date}</td>
                <td>{income.recurringTimeframe}</td>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
                <td>
                  <button className="btn btn-warning">Edit</button>
                </td>
                <td>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(income.incomeId)}
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

export default IncomeList;
