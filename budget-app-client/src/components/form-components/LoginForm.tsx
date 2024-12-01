import React, { FormEventHandler } from 'react';
import { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import '../../styles/LoginForm.css';

type Login = {
  username: string;
  password: string;
};

type User = {
  userId: string;
  firstName: string;
  lastName: string;
  password: string;
  balance: number;
  roleId: number;
};

const LOGIN_DEFAULT: Login = {
  username: '',
  password: '',
};

function LoginForm() {
  const [userLogin, setUserLogin] = useState(LOGIN_DEFAULT);
  const [user, setUser] = useState<User>();
  const [errors, setErrors] = useState([]);

  const url = 'http://localhost:8080/api/authenticate';
  const navigate = useNavigate();
  // const { username } = useParams();

  const handleChange = (event: { target: { name: string; value: string } }) => {
    const newUserLogin: Login = { ...userLogin };
    newUserLogin[event.target.name as keyof Login] = event.target.value;
    setUserLogin(newUserLogin);
  };

  const handleSubmit = (event: { preventDefault: () => void }) => {
    event.preventDefault();
    // console.log(user);
    login(user!);
    navigate(`/user/${user?.userId}`);
    // console.log(user);
  };

  useEffect(() => {
    fetch(`http://localhost:8080/api/appuser/username/${userLogin.username}`)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        setUser(data);
      })
      .catch(console.log);
  }, [userLogin.username]);

  const login = (user: User) => {
    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userLogin),
    };
    fetch(url, init)
      .then((response) => {
        // console.log('try');
        if (response.status === 200 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        console.log(data);
        if (data.jwt_token) {
          console.log('here');
          console.log(user);
          navigate(user.roleId === 1 ? '/admin' : `/user/${user.userId}`);
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };

  return (
    <>
      <section id="loginForm">
        <h1 id="loginFormTitle">Unnamed Budget App</h1>
        <p id="loginFormDescription">
          Finally a way to manage all of your avacado toast and
          Starbucks coffee expenses. Step into the world of financial
          security today!
        </p>
        <div id="error"></div>
        <form onSubmit={handleSubmit} id="loginFormFields">
          <fieldset className="form-group">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Username"
              className="form-control"
              value={userLogin.username}
              onChange={handleChange}
            />
          </fieldset>
          <fieldset className="form-group">
            <input
              type="text"
              id="password"
              name="password"
              placeholder="Password"
              className="form-control"
              value={userLogin.password}
              onChange={handleChange}
            />
          </fieldset>
          <button
            type="submit"
            className="btn btn-outline-primary btn-light mt-2"
          >
            Login
          </button>
          <br></br>
          <Link
            type="button"
            to={'/registration'}
            className="link btn btn-primary mb-5"
          >
            Register
          </Link>
        </form>
      </section>
    </>
  );
}

export default LoginForm;
