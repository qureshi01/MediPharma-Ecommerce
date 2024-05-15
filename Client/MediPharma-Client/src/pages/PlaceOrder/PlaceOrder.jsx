import React, { useContext, useEffect, useState } from 'react';
import './PlaceOrder.css';
import { StoreContext } from '../../context/StoreContext';
import { useCartContext } from '../../context/CartContext';
import axios from 'axios'; // Import Axios
import { useNavigate } from 'react-router-dom';

const PlaceOrder = () => {
  const { cartData, clearCart } = useCartContext();
  const [userData, setUserData] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    // Fetch user data when component mounts
    fetchUserData();
  }, []);

  const fetchUserData = async () => {
    try {
      const userId = getCookie('userId');
      const response = await axios.get(`http://localhost:2399/api/user/fetch?userId=${userId}`);
      if (response.data && response.data.users && response.data.users.length > 0) {
        setUserData(response.data.users[0]);
      }
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  // Function to get cookie value by name
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

  // Function to handle order placement
  const handlePlaceOrder = async () => {
    try {
      const userId = getCookie('userId');
      const response = await fetch(`http://localhost:2399/api/order/add?userId=${userId}`, {
        method: 'POST',
        // If you need to send any additional data in the request body, include it here
        // body: JSON.stringify({ /* Your request body data */ }),
      });

      if (response.ok) {
        const responseData = await response.json();
        console.log('Order placed successfully:', responseData);

        // Clear cart data after placing order
        clearCart();

        navigate(`/ThankYou`)
        // You can add further logic here, such as showing a success message or redirecting the user
      } else {
        console.error('Error placing order:', response.statusText);
        // Handle error scenarios here
      }
    } catch (error) {
      console.error('Error placing order:', error);
      // Handle error scenarios here
    }
  };
  

  return (
    <div className='place-order'>
      <div className="place-order-left">
        <p className='title'>Delivery Information</p>
        <div className="multi-fields">
          <input type="text" placeholder='First name' value={userData?.firstName || ''} readOnly />
          <input type="text" placeholder='Last name' value={userData?.lastName || ''} readOnly />
        </div>
        <input type="text" placeholder='Email address' value={userData?.emailId || ''} readOnly />
        <input type="text" placeholder='Street' value={userData?.address?.street || ''} readOnly />
        <div className="multi-fields">
          <input type="text" placeholder='City' value={userData?.address?.city || ''} readOnly />
          <input type="text" placeholder='State' value={userData?.address?.state || ''} readOnly />
        </div>
        <div className="multi-fields">
          <input type="text" placeholder='Zip Code' value={userData?.address?.pincode || ''} readOnly />
          <input type="text" placeholder='Country' value={userData?.address?.country || ''} readOnly />
        </div>
        <input type="text" placeholder='Phone' value={userData?.phoneNo || ''} readOnly />
      </div>
      <div className="place-order-right">
        <div className="cart-total">
          <h2>Cart Totals</h2>
          <div>
            <div className="cart-total-details">
              <p>Subtotal:-</p>
              <p>Rs.{cartData && cartData.totalCartPrice.toFixed(2)}</p>
            </div>
            <hr />
            <div className="cart-total-details">
              <p>Delivery Fee:-</p>
              <p>Rs.{((cartData.totalCartPrice)*.05).toFixed(2)}</p>
            </div>
            <hr />
            <div className="cart-total-details">
              <b>Total:-</b>
              <b>Rs.{((cartData.totalCartPrice)*.05).toFixed(2)}</b>
            </div>
          </div>
          {/* Adjusted the button */}
          <button onClick={() => handlePlaceOrder()}>PLACE YOUR ORDER</button>
        </div>
      </div>
    </div>
  );
};

export default PlaceOrder;
