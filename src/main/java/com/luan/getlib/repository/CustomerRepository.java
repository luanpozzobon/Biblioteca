package com.luan.getlib.repository;

import com.luan.getlib.models.Customer;

import java.util.HashMap;
import java.util.List;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class CustomerRepository {
    private static final DatabaseAccess<Customer> customerDatabase = new DatabaseAccess<>();
    public static boolean saveCustomer(Customer customer){
        return customerDatabase.save(customer);
    }

    public static Customer findByUsername(String username){
        List<Customer> customers = customerDatabase.find(Customer.class, new HashMap<>(){{
            put("username", username);
        }});
        if(customers.isEmpty()) return new Customer();

        return customers.get(0);
    }
    
    public static boolean usernameExists(String username){
        return findByUsername(username).isEmpty();
    }
    
    public static boolean updateCustomer(Customer customer){
        return customerDatabase.update(customer);
    }
    
    public static boolean deleteCustomer(Customer customer){
        return customerDatabase.delete(customer);
    }
}