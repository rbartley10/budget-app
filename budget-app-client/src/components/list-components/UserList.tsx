import { useState, useEffect } from 'react';

type User = {
  userId: number;
  firstName: string;
  lastName: string;
  userName: string;
  balance: number;
  roleId: number;
};

// const USERS: User[] = [
//   {
//     userId: 1,
//     firstName: 'John',
//     lastName: 'Jones',
//     userName: 'user1',
//     balance: 5000,
//     roleId: 1,
//   },
//   {
//     userId: 2,
//     firstName: 'Jenny',
//     lastName: 'Brown',
//     userName: 'advisor1',
//     balance: 5000,
//     roleId: 1,
//   },
//   {
//     userId: 3,
//     firstName: 'Jim',
//     lastName: 'Owen',
//     userName: 'admin1',
//     balance: 5000,
//     roleId: 1,
//   },
// ];

function UserList() {
  const [users, setUsers] = useState([] as User[]);

  const handleDelete = (userId: number) => {
    const user: User = users.find((u) => u.userId === userId)!;
    if (
      window.confirm(
        `Delete User: ${user.firstName} ${user.lastName} with ID ${user.userId}?`
      )
    ) {
      const init = {
        method: 'DELETE',
      };
      fetch(`${url}/${userId}`, init)
        .then((response) => {
          if (response.status === 204) {
            const newUsers = users.filter((u) => u.userId !== userId);
            setUsers(newUsers);
          } else {
            return Promise.reject(`Unexpected Status Code ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const url = 'http://localhost:8080/api/appuser';

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => setUsers(data))
      .catch(console.log);
  });

  return (
    <>
      <section className="container">
        <h2>Users</h2>
        <button>Add User</button>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Username</th>
              <th>Balance</th>
              <th>Role ID</th>
              <th>&nbsp;</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.userId}>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>{user.userName}</td>
                <td>{user.balance}</td>
                <td>{user.roleId}</td>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
                <td>
                  <button className="btn btn-warning">Edit</button>
                </td>
                <td>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(user.userId)}
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

export default UserList;
