import React, { createContext, useContext, useEffect, useState } from "react";

export const CartContext = createContext();
export const CartProvider = ({ children }) => {
    const [cartData, setCartData] = useState();
    const [cartLoading, setIsCartLoading] = useState(true);
    const [isCartError, setIsCartError] = useState("");

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

    // Function to fetch cart data
    const fetchData = async () => {
        try {
            const userId = getCookie('userId');
            const response = await fetch(`http://localhost:2399/api/cart/fetch?userId=${userId}`);
            const data = await response.json();
            setCartData(data.cartData);
            setIsCartLoading(false);
        } catch (error) {
            setIsCartError(error.message);
            setIsCartLoading(false);
        }
    };

    // Function to clear cart data
    const clearCart = () => {
        setCartData([]);
    };

    useEffect(() => {
        if (getCookie("firstName")) {
            fetchData();
        }
    }, []);

    const value = { cartData, cartLoading, isCartError, setCartData, fetchData, clearCart };

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = () => useContext(CartContext);
