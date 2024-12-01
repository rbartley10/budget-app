import { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import '../../styles/RegistrationForm.css';

const USER_DEFAULT = {
  firstName: '',
  lastName: '',
  userName: '',
  password: '',
};
function RegistrationForm() {
  const [user, setUser] = useState(USER_DEFAULT);
  const [errors, setErrors] = useState([]);
  const url = 'http://localhost:8080/api/register';
  const navigate = useNavigate();
  const { id } = useParams();
  useEffect(() => {
    if (id) {
      fetch(`${url}/${id}`)
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
    } else {
      setUser(USER_DEFAULT);
    }
  }, [id]);
  const handleSubmit = (event: { preventDefault: () => void }) => {
    event.preventDefault();
    if (id) {
      updateUser();
    } else {
      addUser();
      navigate('/');
    }
  };
  const handleChange = (
    event: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { name, type, value } = event.target;
    // If the input type is 'checkbox', safely access `checked`
    const newValue =
      type === 'checkbox' ? (event.target as HTMLInputElement).checked : value;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: newValue,
    }));
  };
  const addUser = () => {
    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    };
    fetch(url, init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code: ${response.status}`);
        }
      })
      .then((data) => {
        console.log(data);
        if (data.userName) {
          navigate('/user');
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };
  const updateUser = () => {
    const init = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    };
    fetch(`${url}/${id}`, init)
      .then((response) => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => {
        if (!data) {
          // happy
          navigate('/user');
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };
  return (
    <>
      <section id="registrationForm">
        <h1 id="registrationFormTitle">Create Account</h1>
        <div id="error"></div>
        <form onSubmit={handleSubmit}>
          <fieldset className="form-group">
            <input
              type="text"
              id="firstName"
              name="firstName"
              placeholder="First Name"
              className="form-control"
              onChange={handleChange}
            />
          </fieldset>
          <fieldset className="form-group">
            <input
              type="text"
              id="lastName"
              name="lastName"
              placeholder="Last Name"
              className="form-control"
              onChange={handleChange}
            />
          </fieldset>
          <fieldset className="form-group">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Username"
              className="form-control"
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
              onChange={handleChange}
            />
          </fieldset>
          <fieldset className="form-group">
            <input
              type="text"
              id="confirmPassword"
              name="confirmPassword"
              placeholder="Confirm Password"
              className="form-control"
              onChange={handleChange}
            />
          </fieldset>
          <button type="submit" className="btn btn-outline-primary mb-3">
            Confirm
          </button>
          <Link to={'/'} className="btn btn-outline-danger">
            Cancel
          </Link>
        </form>
      </section>
    </>
  );
}


export default RegistrationForm;