import React from 'react';
import LoginForm from './components/form-components/LoginForm';
import { BrowserRouter as Router } from 'react-router-dom';
import { Route, Routes } from 'react-router-dom';
import RegistrationForm from './components/form-components/RegistrationForm';
import AdvisorDashboard from './components/dashboard-components/AdvisorDashboard';
import UserDashboard from './components/dashboard-components/UserDashboard';
import AdminDashboard from './components/dashboard-components/AdminDashboard';
import NotFound from './components/NotFound';
import UserList from './components/list-components/UserList';
import IncomeList from './components/list-components/IncomeList';
// import BudgetList from './components/list-components/BudgetList';
import CategoryList from './components/list-components/CategoryList';
import ExpenseList from './components/list-components/ExpenseList';
import ExpenseForm from './components/form-components/ExpenseForm';
import BudgetDashboard from './components/dashboard-components/BudgetDashboard';
 import BudgetForm from './components/form-components/BudgetForm';

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<LoginForm />} />
          <Route path="/registration" element={<RegistrationForm />} />
          <Route path="/user/:userId" element={<UserDashboard />} />
          <Route path="/advisor" element={<AdvisorDashboard />} />
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/incomes" element={<IncomeList />} />
          {/* <Route path="/bugdets/:userId" element={<BudgetList />} /> */}
          <Route path="/budget" element={<BudgetDashboard />} />
           <Route path="/budget-form-add/:userId" element={<BudgetForm/>} /> 
          <Route path="/expenses/:budgetId" element={<ExpenseList />} />
          <Route path="/expense-form-add/:budgetId" element={<ExpenseForm />} />
          <Route path="/expenses/edit/:budgetId/:expenseId" element={<ExpenseForm />} />
          <Route path="/categories" element={<CategoryList />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
