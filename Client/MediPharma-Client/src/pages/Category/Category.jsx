import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './Category.css';
import { useCartContext } from '../../context/CartContext';
import CategoryProductTile from './CategoryProductTile';
import FoodItem from '../../components/ProdItem/ProdItem';


const CategoryById = ({ showLogin }) => {
    const { categoryId } = useParams();
    const [categoryData, setCategoryData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [firstName, setFirstName] = useState("");
    const [userId, setUserId] = useState();
    const [quantity, setQuantity] = useState(0);

    const {fetchData }=useCartContext();
    

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

    useEffect(() => {
        setLoading(true);
        setError(null);

        fetch(`http://localhost:2399/api/product/category/fetch?categoryId=${categoryId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                return response.json();
            })
            .then(data => {
                // Initialize isClicked state for each product
                const updatedData = data.products.map(product => ({
                    ...product,
                    isClicked: false,
                    quantity: 0 // Initialize quantity to 0 for each product
                }));
                setCategoryData(updatedData);
                console.log(updatedData);
                setLoading(false);
            })
            .catch(error => {
                setError(error);
                setLoading(false);
            });
    }, [categoryId]);

    // Function to add or remove product from cart
    const updateCart = (productId, action) => {
        action === "add" ? setQuantity(quantity+1) : setQuantity(quantity-1)
        fetch(`http://localhost:2399/api/cart/${action}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productId: productId,
                userId: Number(userId),
                quantity: Math.abs(quantity) // Ensure quantity is positive
            })
        })
        .then(response => response.json())
        .then(data => {
            console.log("Cart update response:", data);
            fetchData();
            
            // Update the quantity in the local state
            setCategoryData(prevData => {
                return prevData.map(product => {
                    if (product.id === productId) {
                        // For addition, add the quantity, for removal, removetract the quantity
                        const updatedQuantity = quantity > 0 ? (product.quantity || 0) + quantity : Math.max((product.quantity || 0) - Math.abs(quantity), 0);
                        return { ...product, quantity: updatedQuantity };
                    }
                    return product;
                });
            });
           
            
            
            
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
                    <button className='remove-from-cart' onClick={() => updateCart(item.id, "remove")}>
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
    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <>
            <div className='cat-name'>{categoryData[0]?.category?.title}</div>
            <div className='cat-products'>
            {categoryData.length > 0 && categoryData.map(product => (
                            <FoodItem showLogin={showLogin} product={product}
                                
                            />
))}

            </div>
            

        </>
    );
}

export default CategoryById;
