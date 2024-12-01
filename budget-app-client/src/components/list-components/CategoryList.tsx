import { useState, useEffect } from 'react';

type Category = { categoryId: number; categoryName: string };

// const CATEGORIES: Category[] = [
//   { categoryId: 1, categoryName: 'Entertainment' },
//   { categoryId: 2, categoryName: 'Food' },
//   { categoryId: 3, categoryName: 'Car' },
//   { categoryId: 4, categoryName: 'Misc' },
//   { categoryId: 5, categoryName: 'Test Category' },
// ];

function CategoryList() {
  const [categories, setCategories] = useState([] as Category[]);

  const handleDelete = (categoryId: number) => {
    const category: Category = categories.find(
      (c) => c.categoryId === categoryId
    )!;
    if (
      window.confirm(
        `Delete Category: ${category.categoryName} with ID: ${category.categoryId}?`
      )
    ) {
      const init = {
        method: 'DELETE',
      };
      fetch(`${url}/${categoryId}`, init)
        .then((response) => {
          if (response.status === 204) {
            const newCategories = categories.filter(
              (c) => c.categoryId !== categoryId
            );
            setCategories(newCategories);
          } else {
            return Promise.reject(`Unexpected Status Code ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const url = 'http://localhost:8080/api/category';

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected Status Code ${response.status}`);
        }
      })
      .then((data) => setCategories(data))
      .catch(console.log);
  });

  return (
    <>
      <section className="container">
        <h2>Category List</h2>
        <button className="btn btn-info">Add Category</button>
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Categories</th>
              <th>&nbsp;</th>
              <th>&nbsp;</th>
            </tr>
          </thead>
          <tbody>
            {categories.map((category) => (
              <tr key={category.categoryId}>
                <td>{category.categoryName}</td>
                <td>
                  <button className="btn btn-warning">Edit</button>
                </td>
                <td>
                  <button
                    className="btn btn-danger"
                    onClick={() => handleDelete(category.categoryId)}
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

export default CategoryList;
