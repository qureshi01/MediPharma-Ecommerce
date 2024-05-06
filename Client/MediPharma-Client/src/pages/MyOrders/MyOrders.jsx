import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import './MyOrders.css'; // Import CSS file for styling

const MyOrders = () => {
  // State to store the fetched orders
  const [orders, setOrders] = useState([]);

  // Function to fetch orders from the API
  const fetchOrders = async () => {
    try {
      // Get the userId from the cookie
      const userId = Cookies.get('userId');

      if (!userId) {
        console.error('User ID not found in cookie');
        return;
      }

      const response = await axios.get(`http://localhost:2399/api/order/myorder?userId=${userId}`);
      if (response.data.success) {
        // Group orders with the same orderId but different productId
        const groupedOrders = groupOrders(response.data.orders);
        setOrders(groupedOrders);
      } else {
        console.error('Failed to fetch orders:', response.data.responseMessage);
      }
    } catch (error) {
      console.error('Error fetching orders:', error);
    }
  };

  // Function to group orders by orderId
  const groupOrders = (orders) => {
    const groupedOrders = {};
    orders.forEach(order => {
      if (!groupedOrders[order.orderId]) {
        groupedOrders[order.orderId] = [order];
      } else {
        groupedOrders[order.orderId].push(order);
      }
    });
    return Object.values(groupedOrders);
  };

  // Fetch orders when the component mounts
  useEffect(() => {
    fetchOrders();
  }, []);

  return (
    <div className="my-orders-container">
      <h1 className="my-orders-title">My Orders</h1>
      {orders.length > 0 ? (
        <table className="order-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Product Name</th>
              <th>Quantity</th>
              <th>Total Price</th>
              <th>Order Date</th>
              <th>Delivery Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {orders.map(orderGroup => (
              <React.Fragment key={orderGroup[0].orderId}>
                <tr className="order-group">
                  <td rowSpan={orderGroup.length}>{orderGroup[0].orderId}</td>
                  <td>{orderGroup[0].productName}</td>
                  <td>{orderGroup[0].quantity}</td>
                  <td>{(orderGroup[0].totalPrice.toFixed(2))}</td>
                  <td>{orderGroup[0].orderDate}</td>
                  <td>{orderGroup[0].deliveryDate}</td>
                  <td>{orderGroup[0].deliveryStatus}</td>
                </tr>
                {orderGroup.slice(1).map(order => (
                  <tr key={order.productId} className="order-item">
                    <td>{order.productName}</td>
                    <td>{order.quantity}</td>
                    <td>{(order.totalPrice.toFixed(2))}</td>
                    <td>{order.orderDate}</td>
                    <td>{order.deliveryDate}</td>
                    <td>{order.deliveryStatus}</td>
                  </tr>
                ))}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      ) : (
        <p className="no-orders-message">No orders found.</p>
      )}
    </div>
  );
};

export default MyOrders;
