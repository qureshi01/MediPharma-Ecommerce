import React, { useState } from 'react';
import './AdminPage.css';

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

  const [successMessage, setSuccessMessage] = useState('');

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
        <input
          type="number"
          name="categoryId"
          value={productData.categoryId}
          onChange={handleChange}
          placeholder="Enter category ID"
        />
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
