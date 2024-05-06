import React, { useState, useEffect, useContext } from 'react';
import './FoodDisplay.css';
import { StoreContext } from '../../context/StoreContext';
import FoodItem from '../FoodItem/FoodItem';
import axios from 'axios'; // Import axios for making HTTP requests

const FoodDisplay = ({ showLogin }) => {

    const [productData, setProductData] = useState([]);

    useEffect(() => {
        // Fetch data from API when component mounts
        fetch('http://localhost:2399/api/product/all')
            .then(response => response.json())
            .then(data => {
                // Set the fetched data to state
                setProductData(data.products);
                console.log(data.products);
            })
            .catch(error => {
                console.error('Error fetching product data:', error);
            });
    }, []);
    


    return (
        <div className='food-Display' id='food-display'>
            <h2>Medicines at your door steps.</h2>
            <div className="food-display-list">
                            {productData.length > 0 && productData.map(product => (
                            <FoodItem showLogin={showLogin} product={product}
                                
                            />
))}
            
            </div>
        </div>
    );
}

export default FoodDisplay;
