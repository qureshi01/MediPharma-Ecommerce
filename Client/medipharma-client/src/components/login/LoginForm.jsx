import React, { useState } from 'react';
import './LoginForm.css'; // Import the CSS file
import imageSrc from '../../assests/login-bg.jpg'; 

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission (e.g., login logic)
    console.log('Submitted:', { username, password });
  };

  return (
    <div className="login-parent">
    <div className='bg-part'>
    <img className='login-bg' src={imageSrc} alt="Login Bg" />
    </div>

    <div className='form-part'>
    <div className="container"> {/* Use className instead of style */}
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="inputContainer"> {/* Use className instead of style */}
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={handleUsernameChange}
            className="input" 
          />
        </div>
        <div className="inputContainer"> {/* Use className instead of style */}
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            className="input" 
          />
        </div>
        <button type="submit" className="button">Login</button> {/* Use className instead of style */}
      </form>
    </div>
    </div>
    </div>
  );
};

export default LoginForm;
