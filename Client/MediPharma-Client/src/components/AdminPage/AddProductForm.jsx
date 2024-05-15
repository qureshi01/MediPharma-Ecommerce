import React, { useState, useEffect } from 'react';
import './AdminPage.css';
import { Navigate, useNavigate } from 'react-router-dom';

const AddProductForm = () => {
  const [productData, setProductData] = useState({
    title: '',
    description: '',
    quantity: '',
    price: '',
    disc_price: '',
    categoryId: '',
    image: ''
  });

  const [categories, setCategories] = useState([]);
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

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
        const response = await fetch('http://localhost:2399/api/product/category/all');
        const data = await response.json();
        setCategories(data.categories);
        setIsLoading(false);
    } catch (error) {
        setError(error);
        setIsLoading(false);
    }
};

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData({ ...productData, [name]: value });
  };

  const handleAddProduct = async () => {
    try {
      const response = await fetch('http://localhost:2399/api/product/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
      });
      const data = await response.json();
      console.log(data);
      // Reset input fields after successful addition
      if (data.success) {
        setSuccessMessage('Product added successfully');
        setProductData({
          title: '',
          description: '',
          quantity: '',
          price: '',
          disc_price: '',
          categoryId: '',
          image: ''
        });
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className="admin-page">
      <h1>Admin Page</h1>
      <div className="product-management">
        <h2>Product Management</h2>
        <input
          type="text"
          name="title"
          value={productData.title}
          onChange={handleChange}
          placeholder="Enter product title"
        />
        <input
          type="text"
          name="description"
          value={productData.description}
          onChange={handleChange}
          placeholder="Enter product description"
        />
        <input
          type="number"
          name="quantity"
          value={productData.quantity}
          onChange={handleChange}
          placeholder="Enter quantity"
        />
        <input
          type="number"
          name="price"
          value={productData.price}
          onChange={handleChange}
          placeholder="Enter price"
        />
        <input
          type="number"
          name="disc_price"
          value={productData.disc_price}
          onChange={handleChange}
          placeholder="Enter discounted price"
        />
        <select
          name="categoryId"
          value={productData.categoryId}
          onChange={handleChange}
        >
          <option value="">Select Category</option>
          {categories.map(category => (
            <option key={category.id} value={category.id}>{category.title}</option>
          ))}
        </select>
        <input
          type="text"
          name="image"
          value={productData.image}
          onChange={handleChange}
          placeholder="Enter image URL"
        />
        <button onClick={handleAddProduct}>Add Product</button>
        <p>{successMessage}</p>
      </div>
    </div>
  );
};

export default AddProductForm;
