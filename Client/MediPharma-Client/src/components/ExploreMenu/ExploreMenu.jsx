import React, { useState, useEffect } from 'react';
import './ExploreMenu.css';
import { Link } from 'react-router-dom';

const ExploreMenu = ({ category, setCategory }) => {
    const [categories, setCategories] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState();

    useEffect(() => {
        // Fetch categories from API
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const response = await fetch('http://localhost:2399/api/product/category/all');
            const data = await response.json();
            setCategories(data.categories);
            setIsLoading(false);
        } catch (error) {
            setError(error);
            setIsLoading(false);
        }
    };

    return (
        <div className='explore-menu' id='explore-menu'>
            <h1>Explore Our Categories</h1>
            <p className='explore-menu-text'>Select from a wide-ranging assortment of top-quality medications meticulously curated to cater to your healthcare needs. Our commitment is to enhance your well-being, one prescription at a time.</p>
            {isLoading ? (
                <div>Loading...</div>
            ) : error ? (
                <div>OOPS! Something Went wrong</div>
            ) : (
                <div className="explore-menu-list">
                    {categories.map((item, index) => (
                        <Link key={index} to ={`/category/${item.id}`} className='explore-menu-list-item'>
                            <img src="https://static6.depositphotos.com/1112859/621/i/450/depositphotos_6219942-stock-photo-search-of-data-isolated-3d.jpg" alt="Category Image" />
                            <p>{item.title}</p>
                        </Link>
                    ))}
                </div>
            )}
            <hr />
        </div>
    );
};

export default ExploreMenu;