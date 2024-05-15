# **E-commerce Application Using React and Spring Boot**

This project is a simple e-commerce application built with React for the frontend and Spring Boot for the backend. It allows you to manage a collection of items.
MediPharma is a comprehensive online platform designed to provide users with convenient access to a wide range of medical products and supplies. Our goal is to empower individuals to manage their healthcare needs efficiently from the comfort of their homes.

=> Table of Contents
1. Getting Started
2. Prerequisites
3. Installation
4. Running the Application
5. Usage
6. Project Structure
7. Technologies Used
8. Contributing
9. License

=>Getting Started
- Node.js and npm installed.
* Java and Maven for Spring Boot.
+ A compatible database (e.g., MySQL, PostgreSQL) with schema created.

=> Installation


-> Clone the repository:
`git clone https://github.com/qureshi01/MediPharma-Ecommerce`


-> Install frontend dependencies:
`cd client
npm install`


-> Configure the backend:

Configure your database connection in application.properties. For example:


-> MySQL Properties

`spring.datasource.url=jdbc:mysql://localhost:3306/{your-db-name}?useSSL=false&serverTimezone=UTC
`

`spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
`

`spring.datasource.username={your username}
`

`spring.datasource.password={your password}
`

- Specify other configurations like server port and logging as needed.


-> Build the Spring Boot project:

`cd server` 
`mvn clean install` 

Running the Application


-> Start the Spring Boot server:
`cd server
`
`mvn spring-boot:run
`

The backend server should now be running on `http://localhost:2399`.


-> Start the React frontend:
`cd client `
`npm run dev`

The React development server should start and open the application in your default web browser at` http://localhost:5173`.


=> Usage

Access the E-commerce application through the web browser. Perform login, register, and all functions and check the changes in in database.


=> Project Structure

-> The project is structured as follows:
- `client/`: Contains the React frontend code. 
* `server/`: Contains the Spring Boot backend code.


=> Technologies Used

***React***

***Spring Boot***

***Java***

***MySQL*** (or your preferred database)

***npm***

***Maven***


=> Contributing

Feel free to contribute to this project by opening issues or pull requests. Your contributions are welcome!


=> License

This project is licensed under the MediPharma License - see the LICENSE file for details.

Feel free to customize this README file to include specific details about your application, such as project features, API documentation, or any other relevant information. It's essential to provide clear and concise instructions to help users understand and use your application effectively.
