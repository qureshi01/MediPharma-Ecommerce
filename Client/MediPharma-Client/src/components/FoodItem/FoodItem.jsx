import React, { useEffect, useState } from 'react';
import './FoodItem.css';
import { useCartContext } from '../../context/CartContext';

const FoodItem = ({showLogin , product}) => {
    
    const [firstName, setFirstName] = useState(""); // State to hold first name
    const [userId, setUserId] = useState();
    const [quantity, setQuantity] = useState(0);

    const {fetchData}=useCartContext();

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

    useEffect(() => {
        // Set first name from cookie when component mounts
        setFirstName(getCookie('firstName'));
        setUserId(getCookie('userId'));
    }, [showLogin]);

   

    const updateCart = (productId, action) => {
        let updatedQuantity = quantity;
    
        if (action === "add") {
            updatedQuantity++;
        } else if (action === "remove" && updatedQuantity > 0) {
            updatedQuantity--;
        }
    
        fetch(`http://localhost:2399/api/cart/${action}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productId: productId,
                userId: Number(userId),
                quantity: Math.abs(updatedQuantity) // Ensure quantity is positive
            })
        })
        .then(response => response.json())
        .then(data => {
            console.log("Cart update response:", data);
            // Update quantity in the local state after successful API call
            setQuantity(updatedQuantity);
            fetchData();
        })
        .catch(error => {
            console.error("Cart update error:", error);
        });
    };
    
    
    
    

    // Function to render buttons based on quantity
    const renderButtons = (item) => {
        return (
            <div className='prod-buttons'>
                {quantity > 0 ? (
                    <button className='remove-from-cart' onClick={() => updateCart(item.id,"remove")}>
                        <span className="icon minus">-</span>
                    </button>
                ) : null}
                <span className='cart-quantity'>{quantity}</span>
                <button className='add-to-cart' onClick={() => updateCart(item.id, "add")}>
                    <span className="icon">+</span>
                </button>
            </div>
        );
    };

    return (

        <div className='products-par' key = {product.id}>
                <div className='products-tile' key={product.id}>
                    <img className='prod-image' src={product.image} alt={product.title} />
                    <div className='prod-star'>
                        <h4 className='prod-name'>{product.title}</h4>
                        {firstName && 
                                renderButtons(product)
                        }
                    </div>
                    <p className='prod-disc-price'>Rs.{(product.price - product.disc_price).toFixed(2)} <del className='prod-price'>{product.price}</del></p>
                    <p className='save'>You Save Rs.{product.disc_price}</p>
                    <span className='del'>Delivery in 5 days</span>
                </div>
        </div>
    );
}

export default FoodItem;
