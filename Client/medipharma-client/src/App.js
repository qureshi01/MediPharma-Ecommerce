import React from "react";
import { BrowserRouter as Router, Route,Routes } from "react-router-dom";
import LoginForm from "./components/login/LoginForm";
// Import other pages/components and set up your routes

const App = () => {
  return (
    <Router>
    <Routes>
      <Route path="/login" element={<LoginForm />} />
    </Routes>
  </Router>
  );
};

export default App;
