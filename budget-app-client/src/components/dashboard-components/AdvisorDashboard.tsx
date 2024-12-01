import { useState } from 'react';
import { Link } from 'react-router-dom';

type Budget = {
  budgetId: number;
  userId: number;
  goal: string;
  allow_advisor: boolean;
};

const BUDGETS: Budget[] = [
  {
    budgetId: 1,
    userId: 3,
    goal: 'To save for the future',
    allow_advisor: false,
  },
  {
    budgetId: 2,
    userId: 2,
    goal: 'Test description',
    allow_advisor: true,
  },
  {
    budgetId: 3,
    userId: 1,
    goal: 'Test description',
    allow_advisor: true,
  },
];

type User = {
  userId: number;
  firstName: string;
  lastName: string;
  userName: string;
  balance: number;
  roleId: number;
};

const USERS: User[] = [
  {
    userId: 1,
    firstName: 'John',
    lastName: 'Jones',
    userName: 'user1',
    balance: 5000,
    roleId: 1,
  },
  {
    userId: 2,
    firstName: 'Jenny',
    lastName: 'Brown',
    userName: 'advisor1',
    balance: 5000,
    roleId: 1,
  },
  {
    userId: 3,
    firstName: 'Jim',
    lastName: 'Owen',
    userName: 'admin1',
    balance: 5000,
    roleId: 1,
  },
];

function AdvisorDashboard() {
  const [budgets, setBudgets] = useState(BUDGETS);
  const [users, setUsers] = useState(USERS);

  const handleDelete = (userId: number) => {
    const newUsers: User[] = users.filter((u) => u.userId !== userId);

    setUsers(newUsers);
  };

  return (
    <>
      <section>
        <h2>Advisor</h2>
        <h3>Budgets Advised</h3>
        <table>
          <thead>
            <tr>
              <th>User ID</th>
              <th>Goal</th>
              <th>Allow Advisor</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {budgets
              .sort((b1, b2) => b1.userId - b2.userId)
              .map((budget) => (
                <tr key={budget.budgetId}>
                  <td>{budget.userId}</td>
                  <td>{budget.goal}</td>
                  <td>{budget.allow_advisor ? 'yes' : 'no'}</td>
                  <td>
                    <Link to={'/expenses'}>View Expenses</Link>
                  </td>
                </tr>
              ))}
          </tbody>
        </table>
      </section>
    </>
  );
}

export default AdvisorDashboard;
