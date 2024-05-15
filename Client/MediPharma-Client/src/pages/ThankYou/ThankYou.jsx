// ThankYou.jsx
import React from 'react';
import './ThankYou.css'; // Import CSS file for styling

const ThankYou = () => {
  return (
    <div className="thank-you-container">
      <h1 className="thank-you-header">Thank You!</h1>
      <p className="thank-you-message">Your order has been placed successfully.</p>
      <img src="https://cdn.dribbble.com/users/335541/screenshots/7102045/media/5b7237fe7bbfa31531d6e765576f1bc4.jpg?resize=400x300&vertical=center" alt="Thank You Image" className="thank-you-image" />
    </div>
  );
};

export default ThankYou;
