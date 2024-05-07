import React,{ useState } from 'react'
import Navbar from './components/Navbar/Navbar'
import { Route, Routes } from 'react-router-dom'
import Home from './pages/Home/Home'
import Cart from './pages/Cart/Cart'
import PlaceOrder from './pages/PlaceOrder/PlaceOrder'
import Footer from './components/Footer/Footer'
import LoginPopup from './components/LoginPopUp/LoginPopup'
import CategoryById from './pages/Category/Category'
import { CartProvider } from './context/CartContext'
import ThankYou from './pages/ThankYou/ThankYou'
import FoodDisplay from './components/ProdDisplay/ProdDisplay'
import MyOrders from './pages/MyOrders/MyOrders'
import AdminPage from './components/AdminPage/AdminPage'
import AddProductForm from './components/AdminPage/AddProductForm'
import AddCategoryForm from './components/AdminPage/AddCategoryForm'

const App = () => {

  const [showLogin,setShowLogin] = useState(false);

  return (
    <>
    {showLogin?<LoginPopup setShowLogin={setShowLogin}/>:<></>}
    <div className="app">

<CartProvider>
<Navbar setShowLogin={setShowLogin} showLogin={showLogin}/>
      <Routes>
        <Route path='/' element={<Home showLogin={showLogin} />} />
        <Route path='/admin' element={<AdminPage />} />
        <Route path="/add-product" element={<AddProductForm />} />
        <Route path="/add-category" element={<AddCategoryForm />} /> 

        <Route path="/product" element={<FoodDisplay/>} />
        <Route path='/Cart' element={<Cart/>} />
        <Route path='/Order' element={<PlaceOrder/>} />
        <Route path="/Category/:categoryId" element={<CategoryById showLogin={showLogin}/>}  />
        <Route path="/ThankYou" element={<ThankYou />} />
        <Route path="/myorders" element={<MyOrders/>} /> 
      </Routes>

</CartProvider>
   
    </div>
    <Footer/>
    </>
  )
}

export default App