package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.dao.AddressDao;
import com.example.dao.CategoryDao;
import com.example.dao.ProductDao;
import com.example.dao.UserDao;
import com.example.model.Address;
import com.example.model.Category;
import com.example.model.Product;
import com.example.model.User;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Override
    public void run(String... args) throws Exception {
        // Seed Categories & Products if categoryDao is empty
        if (categoryDao.count() == 0) {
            Category cat1 = new Category();
            cat1.setTitle("Analgesics & Painkillers");
            cat1.setDescription("Effective medications for relief from headache, muscle aches, and fever.");
            cat1 = categoryDao.save(cat1);

            Category cat2 = new Category();
            cat2.setTitle("Antibiotics & Anti-Infectives");
            cat2.setDescription("Broad spectrum anti-bacterial and anti-viral pharmaceutical formulations.");
            cat2 = categoryDao.save(cat2);

            Category cat3 = new Category();
            cat3.setTitle("Vitamins & Supplements");
            cat3.setDescription("Essential daily vitamins, minerals, and dietary supplements.");
            cat3 = categoryDao.save(cat3);

            Category cat4 = new Category();
            cat4.setTitle("Personal Care & Dermatology");
            cat4.setDescription("Clinical skin care products, moisturizers, and antiseptic lotions.");
            cat4 = categoryDao.save(cat4);

            // Seed Products
            Product p1 = new Product();
            p1.setTitle("Paracetamol Extra 500mg");
            p1.setDescription("Fast-acting pain reliever and fever reducer for daily relief.");
            p1.setPrice(15.0);
            p1.setDisc_price(3);
            p1.setQuantity(100);
            p1.setImage("https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=500&auto=format&fit=crop&q=60");
            p1.setCategory(cat1);
            productDao.save(p1);

            Product p2 = new Product();
            p2.setTitle("Ibuprofen 400mg Softgel");
            p2.setDescription("Targeted anti-inflammatory pain relief for joint and muscle discomfort.");
            p2.setPrice(22.5);
            p2.setDisc_price(4);
            p2.setQuantity(80);
            p2.setImage("https://images.unsplash.com/photo-1550572017-edf7928994e4?w=500&auto=format&fit=crop&q=60");
            p2.setCategory(cat1);
            productDao.save(p2);

            Product p3 = new Product();
            p3.setTitle("Amoxicillin Trihydrate 500mg");
            p3.setDescription("Effective broad-spectrum antibiotic prescribed for bacterial infections.");
            p3.setPrice(35.0);
            p3.setDisc_price(5);
            p3.setQuantity(50);
            p3.setImage("https://images.unsplash.com/photo-1471864190281-a93a3070b6de?w=500&auto=format&fit=crop&q=60");
            p3.setCategory(cat2);
            productDao.save(p3);

            Product p4 = new Product();
            p4.setTitle("Vitamin C 1000mg + Zinc");
            p4.setDescription("Effervescent immune booster tablets for daily wellness and vital protection.");
            p4.setPrice(28.0);
            p4.setDisc_price(5);
            p4.setQuantity(120);
            p4.setImage("https://images.unsplash.com/photo-1577401239170-897942555fb3?w=500&auto=format&fit=crop&q=60");
            p4.setCategory(cat3);
            productDao.save(p4);

            Product p5 = new Product();
            p5.setTitle("Daily Multivitamin Complex");
            p5.setDescription("Complete spectrum of essential vitamins, B-complex, and trace minerals.");
            p5.setPrice(45.0);
            p5.setDisc_price(8);
            p5.setQuantity(90);
            p5.setImage("https://images.unsplash.com/photo-1584017911766-d451b3d0e843?w=500&auto=format&fit=crop&q=60");
            p5.setCategory(cat3);
            productDao.save(p5);

            Product p6 = new Product();
            p6.setTitle("Hydrating Skin Defense Lotion");
            p6.setDescription("Dermatologist tested moisturizing lotion for sensitive skin.");
            p6.setPrice(32.0);
            p6.setDisc_price(4);
            p6.setQuantity(60);
            p6.setImage("https://images.unsplash.com/photo-1608248597263-0057e57b4522?w=500&auto=format&fit=crop&q=60");
            p6.setCategory(cat4);
            productDao.save(p6);
        }

        // Seed Admin and Demo User if no user exists
        if (userDao.count() == 0) {
            Address adminAddr = new Address();
            adminAddr.setStreet("100 Healthcare Blvd");
            adminAddr.setCity("Medical City");
            adminAddr.setPincode(110001);
            adminAddr = addressDao.save(adminAddr);

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmailId("admin@medipharma.com");
            admin.setPassword("admin123");
            admin.setPhoneNo("9876543210");
            admin.setRole("Admin");
            admin.setAddress(adminAddr);
            userDao.save(admin);

            Address userAddr = new Address();
            userAddr.setStreet("42 Wellness Ave");
            userAddr.setCity("Healthtown");
            userAddr.setPincode(560001);
            userAddr = addressDao.save(userAddr);

            User customer = new User();
            customer.setFirstName("Hashim");
            customer.setLastName("Qureshi");
            customer.setEmailId("user@medipharma.com");
            customer.setPassword("user123");
            customer.setPhoneNo("9123456789");
            customer.setRole("Customer");
            customer.setAddress(userAddr);
            userDao.save(customer);
        }
    }
}
