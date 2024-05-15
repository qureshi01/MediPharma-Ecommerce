import React from 'react';
import './Header.css';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <div className='Header'>
      <div className="Header-Contents">
        <h2>Order Your Medicines here</h2>
        <p>Select from a wide-ranging assortment of top-quality medications meticulously curated to cater to your healthcare needs. Our commitment is to enhance your well-being, one prescription at a time.</p>
        <Link to="/product"> {/* Link to the products page */}
          <button>View menu</button>
        </Link>
      </div>
    </div>
  );
};

export default Header;
