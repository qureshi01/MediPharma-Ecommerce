

import React, { useContext, useState, useEffect } from 'react';
import './Navbar.css';
import { assets } from '../../assets/assets';
import { Link } from 'react-router-dom';
import { StoreContext } from '../../context/StoreContext';
import Cookies from 'js-cookie';
import { useCartContext } from '../../context/CartContext';

const Navbar = ({ setShowLogin, showLogin }) => {
    const { getTotalCartAmount } = useContext(StoreContext);
    const [menu, setMenu] = useState("menu");
    const [firstName, setFirstName] = useState(""); // State to hold first name
    const [cartItemsLength, setCartItemsLength] = useState(0);

    const { cartData, cartLoading } = useCartContext();

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

    const handleSignout = () => {
        Cookies.remove('role');
        Cookies.remove('firstName');
        Cookies.remove('userId');
        setFirstName('');
        window.location.href = "/";
    }

    useEffect(() => {
        // Set first name from cookie when component mounts
        setFirstName(getCookie('firstName'));
        if (!cartLoading) {
            // Check if cartData exists and has cartProducts array
            setCartItemsLength(cartData?.cartProducts?.length || 0);
        }
    }, [showLogin, cartLoading, cartData]); // Empty dependency array to run only on mount

    const isLoggedIn = !!firstName; // Check if user is logged in

    return (
        <div className='navbar'>
            <div className="navbar-left">
                <Link to='/'><img src={assets.logo} alt="" className="logo" /></Link>
                <ul className="navbar-menu">
                    <li><Link to='/' onClick={() => setMenu("home")} className={menu === "home" ? "active" : ""}>Home</Link></li>
                    <li><a href='#explore-menu' onClick={() => setMenu("menu")} className={menu === "menu" ? "active" : ""}>Menu</a></li>
                    <li><a href='#app-download' onClick={() => setMenu("mobile-app")} className={menu === "mobile-app" ? "active" : ""}>Mobile-App</a></li>
                    <li><a href='#footer' onClick={() => setMenu("contact us")} className={menu === "contact us" ? "active" : ""}>Contact Us</a></li>
                    
                </ul>
            </div>
            <div className="navbar-right">
                <div className="navbar-search">
                    <img src={assets.search_icon} alt="" />
                </div>
                {isLoggedIn && 
                    <div className="order-nav">
                        <Link to='/myorders' className="order-link">My Orders</Link>
                    </div>
                }

                <div className="navbar-cart">
                    <Link to='/cart' className="cart-symbol">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2ECvUVo40V25AiQMpdOCLxWbE-u85Pz01XRYcNpekOA&s" alt="" />
                        <span className="cartNumber">{cartItemsLength}</span>
                    </Link>
                    <div className={getTotalCartAmount() === 0 ? "" : "dot"}></div>
                </div>
                {isLoggedIn ? (
                    // If user is logged in, display first name
                    <div className='user-div'>
                        <span>{firstName}</span>
                        <button onClick={() => handleSignout()}>Sign out</button>
                    </div>
                ) : (
                    // If user is not logged in, display sign-in button
                    <button className="sign-in-button" onClick={() => setShowLogin(true)}>Sign in</button>

                )}
            </div>
        </div>
    );
};

export default Navbar;
