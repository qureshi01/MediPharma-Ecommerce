# 💊 MediPharma - Full-Stack Healthcare E-Commerce Platform

MediPharma is a full-stack web application designed to provide users with convenient online access to a wide range of medical products, supplies, and prescription-free pharmaceuticals. Built with a modern **React + Vite** frontend and a **Spring Boot** RESTful backend, MediPharma offers an intuitive shopping experience for customers and a control dashboard for administrators.

---

## 🌟 Key Features

### 🛒 Customer Features
* **Interactive Product Catalog**: Browse medical products categorized by therapeutic area with responsive image displays and price discounts.
* **Category Filtering**: Seamlessly filter products by categories (e.g., *Analgesics & Painkillers*, *Antibiotics*, *Vitamins & Supplements*, *Personal Care*).
* **Dynamic Shopping Cart**: Real-time quantity adjustments (increment, decrement, delete) with live subtotal and total calculations using React Context API and browser cookies.
* **Seamless Checkout & Address Auto-fill**: Order placement flow automatically pre-filling user delivery addresses, phone numbers, and contact details.
* **Order History & Status Tracking**: Track individual order history (`My Orders`), view order IDs, delivery dates, and status updates (*Pending*, *Delivered*, etc.).
* **User Authentication**: Secure Registration and Sign-In with client-side cookie session management.

### 🛡️ Admin Dashboard
* **Product Management**: Add new pharmaceutical products with title, description, price, discount price, stock quantity, category link, and image URL.
* **Category Management**: Create and manage medical product categories.
* **Global Order Fulfillment**: View all customer orders across the platform and update order delivery statuses and scheduled delivery times.

### ⚙️ Backend & Architecture Features
* **Zero-Config Instant Execution (H2 In-Memory DB)**: Runs out-of-the-box using an embedded H2 database in MySQL compatibility mode—no local database installation required!
* **Production MySQL Support**: Configured to switch easily to a local or remote MySQL 8.x server.
* **Automatic Data Seeding**: Automatically populates sample medical categories, products, and default Admin/Customer accounts on first launch if the database is empty.
* **Non-blocking Email Notifications**: Integrated Spring Mail Sender for user registration and order confirmations.
* **CORS-Enabled REST APIs**: Configured `@CrossOrigin("*")` on all REST controllers for smooth client-server communication.

---

## 🛠️ Technology Stack

| Layer | Technology / Library |
| :--- | :--- |
| **Frontend Framework** | React 18, Vite |
| **Routing & Navigation** | React Router DOM (v6) |
| **State & Cookies** | React Context API, `js-cookie` |
| **HTTP Client** | Axios, Fetch API |
| **Styling** | Vanilla CSS3 (Custom responsive styling, Modal popups, Badges) |
| **Backend Framework** | Spring Boot 3.2.4 (Java 17/21) |
| **Data Access / ORM** | Spring Data JPA, Hibernate ORM |
| **Databases** | H2 In-Memory (Default Dev) / MySQL 8.x (Production) |
| **Build Tools** | Maven, npm |

---

## 📂 Project Directory Structure

```text
MediPharma-Ecommerce/
├── Client/
│   └── MediPharma-Client/        # React + Vite Frontend Application
│       ├── public/               # Static assets & favicon
│       ├── src/
│       │   ├── assets/           # UI graphics & icons
│       │   ├── components/       # Reusable components (Navbar, Footer, AdminPage, etc.)
│       │   ├── context/          # CartContext & StoreContext for global state
│       │   ├── pages/            # View pages (Home, Cart, PlaceOrder, MyOrders, etc.)
│       │   ├── App.jsx           # Application routing & layout
│       │   ├── main.jsx          # Entry point
│       │   └── index.css         # Base styles & design tokens
│       ├── package.json          # Node dependencies & scripts
│       └── vite.config.js        # Vite dev server configuration
│
└── Server/
    └── MediPharma/               # Spring Boot Backend Application
        ├── src/main/java/com/example/
        │   ├── config/           # DataInitializer (Auto-seeding)
        │   ├── controller/       # REST Endpoints (UserController, ProductController, etc.)
        │   ├── dao/              # JPA Repositories (UserDao, ProductDao, OrderDao, etc.)
        │   ├── dto/              # Data Transfer Objects (Requests & Responses)
        │   ├── model/            # JPA Entities (User, Address, Category, Product, Cart, Orders)
        │   ├── resource/         # Service layer implementation
        │   └── MediPharmaApplication.java
        ├── src/main/resources/
        │   └── application.properties # Spring configuration & Database profiles
        └── pom.xml               # Maven dependencies
```

---

## 🚀 Getting Started

### Prerequisites
Make sure you have the following installed on your machine:
* **Node.js** (v18.0 or higher) & **npm**
* **Java Development Kit (JDK)** (version 17 or 21)
* **Apache Maven** (v3.8 or higher)

---

### 📥 1. Clone the Repository

```bash
git clone https://github.com/qureshi01/MediPharma-Ecommerce.git
cd MediPharma-Ecommerce
```

---

### ⚙️ 2. Start the Backend Server (Spring Boot)

Navigate to the `Server/MediPharma` directory and launch the application:

```bash
cd Server/MediPharma
mvn spring-boot:run
```

* The backend server will start on **`http://localhost:2399`**.
* **Database**: Uses H2 In-Memory DB by default. Tables and sample medical products are automatically created and seeded.
* **H2 Console**: Accessible at `http://localhost:2399/h2-console` (JDBC URL: `jdbc:h2:mem:MediPharmaDB`, User: `sa`, Password: *blank*).

---

### 💻 3. Start the Frontend Application (React)

Open a new terminal window, navigate to the `Client/MediPharma-Client` directory, install packages, and run the development server:

```bash
cd Client/MediPharma-Client
npm install
npm run dev
```

* The frontend application will start on **`http://localhost:5173`**.

---

## 🔑 Default Seeded Accounts

The application automatically seeds test accounts on first launch for immediate testing:

| Role | Email ID | Password | Access Capabilities |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@medipharma.com` | `admin123` | Access `/admin` dashboard, add products, add categories, view & update orders |
| **Customer** | `user@medipharma.com` | `user123` | Browse store, add items to cart, place orders, view `/myorders` |

---

## 📡 REST API Endpoint Reference

### 👤 User Operations (`/api/user`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/user/register` | Register a new user with address details |
| `POST` | `/api/user/login` | Authenticate user & return role/userId details |
| `GET` | `/api/user/fetch?userId={id}` | Fetch user profile by ID |

### 💊 Product & Category Operations (`/api/product`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/product/all` | Get all available medical products |
| `GET` | `/api/product/id?productId={id}` | Get product details by ID |
| `GET` | `/api/product/category/fetch?categoryId={id}` | Fetch products by category ID |
| `POST` | `/api/product/add` | Add a new product (Admin) |
| `GET` | `/api/product/category/all` | Fetch all categories |
| `POST` | `/api/product/category/add` | Add a new category (Admin) |

### 🛍️ Cart Operations (`/api/cart`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/cart/add` | Increment item quantity in cart |
| `POST` | `/api/cart/remove` | Decrement item quantity in cart |
| `GET` | `/api/cart/fetch?userId={id}` | Get user's cart contents and total price |
| `GET` | `/api/cart/delete?cartId={id}` | Remove item completely from cart |

### 📦 Order Operations (`/api/order/`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/order/add?userId={id}` | Place order for items currently in cart |
| `GET` | `/api/order/myorder?userId={id}` | Fetch purchase history for logged-in user |
| `GET` | `/api/order/admin/allorder` | Fetch all orders across platform (Admin) |
| `GET` | `/api/order/admin/showorder?orderId={id}` | Fetch orders by specific order ID |
| `POST` | `/api/order/admin/deliveryStatus/update` | Update delivery date, status & time (Admin) |

---

## 🗄️ Database Configuration Guide

To switch from the default **H2 In-Memory Database** to a local **MySQL Database**:

1. Open `Server/MediPharma/src/main/resources/application.properties`.
2. Comment out the H2 configuration lines and uncomment the MySQL properties:

```properties
# MySQL Connection Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/MediPharmaDB?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

3. Ensure MySQL server is running on port `3306` and create the database `MediPharmaDB`.

---

## 📄 License

This project is licensed under the MediPharma License - see the `LICENSE` file for details.
