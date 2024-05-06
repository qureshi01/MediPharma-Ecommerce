import React, { useState } from 'react';
import './AdminPage.css';
import { Link } from 'react-router-dom';

const AdminPage = () => {
  const [orders, setOrders] = useState([]);
  const [showOrders, setShowOrders] = useState(false);

  const handleViewAllOrders = async () => {
    try {
      if (showOrders) {
        setShowOrders(false); // Toggle showOrders state
      } else {
        const response = await fetch('http://localhost:2399/api/order/admin/allorder');
        const data = await response.json();
        console.log('Fetched orders:', data);
        if (data.success) {
          setOrders(data.orders);
          setShowOrders(true);
        } else {
          console.error('Error fetching orders:', data.responseMessage);
        }
      }
    } catch (error) {
      console.error('Error fetching orders:', error);
    }
  };

  const handleUpdateDeliveryStatus = async (orderId) => {
    const newDeliveryStatus = prompt('Enter new delivery status:');
    const deliveryTime = prompt('Enter delivery time:');
    const deliveryDate = prompt('Enter delivery date (DD-MM-YYYY):');

    if (newDeliveryStatus && deliveryTime && deliveryDate) {
      try {
        const response = await fetch('http://localhost:2399/api/order/admin/deliveryStatus/update', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            orderId,
            deliveryStatus: newDeliveryStatus,
            deliveryTime,
            deliveryDate
          })
        });
        const data = await response.json();
        console.log(data);
        // Refresh orders after updating delivery status
        handleViewAllOrders();
      } catch (error) {
        console.error('Error updating delivery status:', error);
      }
    }
  };

  return (
    <div className="admin-page">
      <h1>Admin Page</h1>

      <div className="management-section">
        <div className="management-item">
          <h2>Product Management</h2>
          <Link to="/add-product">Add Product</Link>
        </div>
        <div className="management-item">
          <h2>Category Management</h2>
          <Link to="/add-category">Add Category</Link>
        </div>
        <div className="management-item">
          <h2>Order Management</h2>
          <button onClick={handleViewAllOrders}>
            {showOrders ? 'Hide Orders' : 'View All Orders'}
          </button>
        </div>
      </div>

      {showOrders && (
        <div className="order-table">
          <table>
            <thead>
              <tr>
                <th>Order ID</th>
                <th>User Name</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Total Price</th>
                <th>Order Date</th>
                <th>Delivery Status</th>
                <th>Update Status</th> {/* New column header */}
              </tr>
            </thead>
            <tbody>
              {orders.map(order => (
                <tr key={order.orderId}>
                  <td>{order.orderId}</td>
                  <td>{order.userName}</td>
                  <td>{order.productName}</td>
                  <td>{order.quantity}</td>
                  <td>{(order.totalPrice.toFixed(2))}</td>
                  <td>{order.orderDate}</td>
                  <td>{order.deliveryStatus}</td>
                  <td>
                    <button onClick={() => handleUpdateDeliveryStatus(order.orderId)}>
                      Update
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default AdminPage;
