import React, { useState, useEffect } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';

const AddCategoryForm = () => {
  const [categoryData, setCategoryData] = useState({
    title: '',
    description: ''
  });

  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const getCookie = (name) => {
      const cookies = document.cookie.split(';');
      for (let cookie of cookies) {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName.trim() === name) {
          return cookieValue;
        }
      }
      return null;
    };
  
    const roleCookie = getCookie('role');

  
    console.log(roleCookie, "is role");
  
    if (roleCookie !== "admin") {
      navigate("/");
    }   
  }, []); // <-- empty dependency array removed

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCategoryData({ ...categoryData, [name]: value });
  };

  const handleAddCategory = async () => {
    try {
      const response = await fetch('http://localhost:2399/api/product/category/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(categoryData)
      });
      const data = await response.json();
      console.log(data);
      // Reset input fields after successful addition
      if (data.success) {
        setSuccessMessage('Category added successfully');
        setCategoryData({
          title: '',
          description: ''
        });
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="admin-page">
      <h1>Admin Page</h1>
      <div className="category-management">
        <h2>Category Management</h2>
        <input
          type="text"
          name="title"
          value={categoryData.title}
          onChange={handleChange}
          placeholder="Enter category title"
        />
        <input
          type="text"
          name="description"
          value={categoryData.description}
          onChange={handleChange}
          placeholder="Enter category description"
        />
        <button onClick={handleAddCategory}>Add Category</button>
        <p>{successMessage}</p>
      </div>
    </div>
  );
};

export default AddCategoryForm;
