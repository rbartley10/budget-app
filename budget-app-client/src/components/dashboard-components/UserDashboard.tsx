import { useEffect, useState } from 'react';
import BudgetList from '../list-components/BudgetList';
import { Link, useParams } from 'react-router-dom';
import '../../styles/UserDashboard.css';

interface User {
  userId: number;
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  roleId: number;
}

const DEFAULT_USER: User = {
  userId: 0,
  firstName: "",
  lastName: "",
  username: "",
  password: "",
  roleId: 0
};

function UserDashboard() {
  const [user, setUser] = useState(DEFAULT_USER);
  const { userId } = useParams<{ userId: string }>();

  useEffect(() => {
    fetch(`http://localhost:8080/api/user/${parseInt(userId!)}`)
      .then(response => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then(data => setUser(data))
      .catch(console.log);
  },[]);

  return (
    <>
      <section className="dashboard-container">
        <nav className="dashboard-navbar">
          <Link to="/" className="nav-link">
            Home
          </Link>
          <Link to="/incomes" className="nav-link">
            View Incomes
          </Link>
          <Link to="/expenses" className="nav-link">
            View Expenses
          </Link>
          <Link to="/categories" className="nav-link">
            View Categories
          </Link>
        </nav>
        <h2 className="dashboard-title">Hello, {user.username}!</h2>
        <div className="dashboard-content">
          <BudgetList userId={userId!} />
        </div>
      </section>
    </>
  );
}

export default UserDashboard;