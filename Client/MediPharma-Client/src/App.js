import Header from "./pages/Header";
import AddProductForm from "./productComponent/AddProductForm";
import { Routes, Route } from "react-router-dom";
import ContactUs from "./pages/ContactUs";
import AboutUs from "./pages/AboutUs";
import AddCategoryForm from "./productComponent/AddCategoryForm";
import HomePage from "./pages/HomePage";
import Product from "./pages/Product";
import AddUserForm from "./userComponent/AddUserForm";
import UserLoginForm from "./userComponent/UserLoginForm";
import MyCart from "./userComponent/MyCart";
import AddCardDetails from "./pages/AddCardDetails";
import MyOrder from "./userComponent/MyOrder";
import AllOrders from "./userComponent/AllOrders";
import SearchOrder from "./userComponent/SearchOrder";
import AdminLoginPage from "./userComponent/AdminLoginPage";
import ForgetPassword from "./userComponent/ForgetPassword";

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/home/all/product/categories" element={<HomePage />} />
        <Route path="contact" element={<ContactUs />} />
        <Route path="about" element={<AboutUs />} />
        <Route path="addproduct" element={<AddProductForm />} />
        <Route path="addcategory" element={<AddCategoryForm />} />
        <Route path="/product" element={<Product />} />
        <Route path="/user/login" element={<UserLoginForm />} />
        <Route path="/user/admin/login" element={<AdminLoginPage />} />
        <Route
          path="/home/product/category/:categoryId/:categoryName"
          element={<HomePage />}
        />
        <Route
          path="/product/:productId/category/:categoryId"
          element={<Product />}
        />
        <Route path="/user/mycart" element={<MyCart />} />
        <Route path="/user/order/payment" element={<AddCardDetails />} />
        <Route path="/user/myorder" element={<MyOrder />} />
        <Route path="/user/admin/allorder" element={<AllOrders />} />
        <Route path="/user/admin/searchOrder" element={<SearchOrder />} />
        <Route path="/user/customer/register" element={<AddUserForm />} />
        <Route path="/user/admin/register" element={<AddUserForm />} />
        <Route path="/user/forget-password" element={<ForgetPassword />} />
      </Routes>
    </div>
  );
}

export default App;