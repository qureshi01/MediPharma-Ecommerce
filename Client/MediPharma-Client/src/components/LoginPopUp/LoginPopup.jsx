import React, { useState } from 'react';
import './LoginPopup.css';
import { assets } from '../../assets/assets';
import { Navigate, useNavigate } from 'react-router-dom';

const LoginPopup = ({ setShowLogin }) => {
    const [currState, setCurrState] = useState("Login");
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [emailId, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phoneNo, setPhoneNo] = useState('');
    const [street, setStreet] = useState('');
    const [city, setCity] = useState('');
    const [pincode, setPincode] = useState('');
    const [role, setRole] = useState('');
    const [loading, setLoading] = useState(false); // Add loading state
    const [error, setError] = useState(''); // Add error state

    const navigate = useNavigate();
    const handleInputChange = (e, inputType) => {
        const value = e.target.value;
        switch (inputType) {
            case 'firstName':
                setFirstName(value);
                break;
            case 'lastName':
                setLastName(value);
                break;
            case 'emailId':
                setEmail(value);
                break;
            case 'password':
                setPassword(value);
                break;
            case 'phoneNo':
                setPhoneNo(value);
                break;
            case 'street':
                setStreet(value);
                break;
            case 'city':
                setCity(value);
                break;
            case 'pincode':
                setPincode(value);
                break;
            case 'role':
                setRole(value);
                break;
            default:
                break;
        }
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        setLoading(true); // Set loading to true when form is submitted
        setError(''); // Clear previous error
        if (currState === "Login") {
            try {
                const response = await fetch('http://localhost:2399/api/user/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ emailId, password })
                });
                const data = await response.json();
                console.log("Login response:", data);
                setLoading(false); // Set loading to false after successful login
                if (data.success) {
                    document.cookie = `role=${data.role}; path=/`; // Set role in cookie
                    document.cookie = `firstName=${data.firstName}; path=/`; // Set firstName in cookie
                    document.cookie = `userId=${data.userId}; path=/`; // Set userId in cookie
                    setShowLogin(false); // Close the form

                    console.log(data.role,"isRole");
                    data.role === "admin" ?
                    navigate("/admin"):navigate("/");
                }
                // Handle login success
            } catch (error) {
                console.error("Login error:", error);
                setLoading(false); // Set loading to false after login error
                setError('Failed to login. Please check your credentials.'); // Set error message
                // Handle login error
            }
        } else {
            try {
                const response = await fetch('http://localhost:2399/api/user/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ firstName, lastName, emailId, password, phoneNo, street, city, pincode, role })
                });
                const data = await response.json();
                console.log("Sign up response:", data);
                if (data.success) {
                    setLoading(false); // Set loading to false after successful signup
                    document.cookie = `role=${data.role}; path=/`; // Set role in cookie
                    document.cookie = `firstName=${data.firstName}; path=/`; // Set firstName in cookie
                    document.cookie = `userId=${data.userId}; path=/`; // Set userId in cookie
                    setShowLogin(false); // Close the form

                    console.log(data.role,"isRole");
                    data.role === "admin" ?
                    navigate("/admin"):navigate("/");
                } else {
                    setLoading(false); // Set loading to false after signup error
                    setError(data.responseMessage); // Set error message
                }
            } catch (error) {
                console.error("Sign up error:", error);
                setLoading(false); // Set loading to false after signup error
                setError('Failed to sign up. Please try again.'); // Set error message
                // Handle signup error
            }
        }
    };
    
    

    return (
        <div className='login-popup'>
            <form className="login-popup-container" onSubmit={handleFormSubmit}>
                <div className="login-popup-title">
                    <h2>{currState}</h2>
                    <img onClick={() => setShowLogin(false)} src={assets.cross_icon} alt="" />
                </div>
                <div className="login-popup-inputs">
                    {currState === "Login" ? null : 
                        <>
                            <input type="text" placeholder='First Name' value={firstName} onChange={(e) => handleInputChange(e, 'firstName')} required />
                            <input type="text" placeholder='Last Name' value={lastName} onChange={(e) => handleInputChange(e, 'lastName')} required />
                        </>
                    }
                    {currState === "Login" ? null :
                        <>
                            <input type="email" placeholder='Your emailId' value={emailId} onChange={(e) => handleInputChange(e, 'emailId')} required />
                            <input type="password" placeholder='Password' value={password} onChange={(e) => handleInputChange(e, 'password')} required />
                            <input type="text" placeholder='Phone Number' value={phoneNo} onChange={(e) => handleInputChange(e, 'phoneNo')} required />
                            <input type="text" placeholder='Street' value={street} onChange={(e) => handleInputChange(e, 'street')} required />
                            <input type="text" placeholder='City' value={city} onChange={(e) => handleInputChange(e, 'city')} required />
                            <input type="text" placeholder='Pincode' value={pincode} onChange={(e) => handleInputChange(e, 'pincode')} required />
                            <select value={role} onChange={(e) => handleInputChange(e, 'role')} required>
                                <option value="">Select Role</option>
                                <option value="admin">Admin</option>
                                <option value="user">User</option>
                            </select>
                        </>
                    }
                    {currState === "Login" ? 
                        <>
                            <input type="emailId" placeholder='Your emailId' value={emailId} onChange={(e) => handleInputChange(e, 'emailId')} required />
                            <input type="password" placeholder='Password' value={password} onChange={(e) => handleInputChange(e, 'password')} required />
                        </>
                        : null
                    }
                </div>
                <button type="submit" disabled={loading}>{loading ? 'Submitting...' : (currState === "Sign Up" ? "Create account" : "Login")}</button>
                {error && <div className="error-message">{error}</div>} {/* Display error message if error exists */}
                <div className="login-popup-condition">
                    <input type="checkbox" required />
                    <p>By continuing, I agree to the terms of use & privacy Policy</p>
                </div>
                {currState === "Login"
                    ? <p>Create a new account? <span onClick={() => setCurrState("Sign Up")}>Click here</span></p>
                    : <p>Already have an account? <span onClick={() => setCurrState("Login")}>Login here</span></p>
                }
            </form>
        </div>
    );
};

export default LoginPopup;