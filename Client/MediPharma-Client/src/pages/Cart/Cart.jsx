import React, { useContext, useEffect, useState } from 'react';
import './Cart.css';
import { StoreContext } from '../../context/StoreContext';
import { useNavigate } from 'react-router-dom';
import { useCartContext } from '../../context/CartContext';


const Cart = () => {
  const navigate = useNavigate();
  //const [cartData, setCartData] = useState([]);
  //const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { cartData, fetchData, cartLoading }=useCartContext();

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

// const fetchData = async () => {
//   try {
//     const userId = getCookie('userId');
//     const response = await fetch (`http://localhost:2399/api/cart/fetch?userId=${userId}`);
//     // if (!response.ok) {
//     //   throw new Error('Failed to fetch data');
//     // }
//     const data = await response.json();
//     setCartData(data.cartData);
//     setLoading(false);
//   } catch (error) {
//     setError(error.message);
//     setLoading(false);
//   }
// };

const deleteCart = async (cartId) => {
  try {
    const response = await fetch (`http://localhost:2399/api/cart/delete?cartId=${cartId}`, {
      method: 'GET'
    });
    if (!response.ok) {
      throw new Error('Failed to delete item from cart');
    }
    // After successful deletion, refetch cart data
    fetchData();
  } catch (error) {
    setError(error.message);
  }
};



  useEffect(() => {
    

    fetchData();
  }, [cartLoading]);



  if (cartLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className='cart'> 
      {cartData && cartData.cartProducts && cartData.cartProducts.length === 0 ? (
        <div className="no-cart-items">
          <div className="image-container">
            <img src="https://cdn-icons-png.flaticon.com/512/11329/11329060.png" alt="No Cart Items" />
          </div>
          <p>Oops! Your cart is empty.</p>
          <p>Start adding items to your cart to see them here.</p>
        </div>
      ) : (
        <div className="cart-items">
          <div className="cart-items-title">
            <p>Items</p>
            <p>Title</p>
            <p>Price</p>
            <p>Quantity</p>
            <p>Total</p>
            <p>Remove</p>
          </div>
          <hr/>
          {cartData && cartData.cartProducts && cartData.cartProducts.map((item) => (
            <div key={item.cartId}>
              <div className='cart-items-title cart-items-item'>
                <img src={item.productImage} alt="" />
                <p>{item.productName}</p>
                {/* Assuming you have a price property in your API response */}
                <p>Rs.{(item.price).toFixed(2)}</p>
                <p>{item.quantity}</p>
                {/* Assuming you have a totalPrice property in your API response */}
                <p>Rs.{item.totalPrice.toFixed(2)}</p>
                <p onClick={() => deleteCart(item.cartId)} className='cross'>x</p>
              </div>
              <hr/>
            </div>
          ))}
        </div>
      )}
      {cartData && cartData.cartProducts && cartData.cartProducts.length > 0 && 
        <div className='cart-bottom'>
          
          <div className="cart-total">
            <h2>Cart Totals</h2>
            <div>
              <div className="cart-total-details">
                <p>Subtotal:</p>
                <p>Rs.{cartData.totalCartPrice.toFixed(2)}</p>
              </div>
              <hr />
              <div className="cart-total-details">
                <p>Delivery Fee:</p>
                <p>Rs.{((cartData.totalCartPrice)*.05).toFixed(2)}</p>
              </div>
              <hr />
              <div className="cart-total-details">
                <b>Total:-</b>
                <b>Rs.{(cartData.totalCartPrice + ((cartData.totalCartPrice)*.05)).toFixed(2)}</b>
              </div>
            </div>
            <button onClick={() => navigate('/Order')}>PROCEED TO CHECKOUT</button>
          </div>
  
          <div className='cart-promocode'>
            <div>
              <p>If you have a promo code, Enter it here</p> 
              <div className="cart-promocode-input">
                <input type="text" placeholder='promo code' />
                <button>Submit</button>
              </div>  
            </div>
          </div>
        
        </div>
      }
    </div>
  );
  
}

export default Cart;