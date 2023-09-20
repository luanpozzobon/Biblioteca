package com.luan.getlib.repository;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Operation;

import java.util.HashMap;
import java.util.List;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class OperationRepository {

    private static final DatabaseAccess<Operation> operationDatabase = new DatabaseAccess<>();
    public static boolean saveOperation(Operation operation){
        return operationDatabase.save(operation);
    }

    public static Operation findById(int operationId) {
        List<Operation> operations = operationDatabase.find(Operation.class, new HashMap<>(){{
            put("id", operationId);
        }});
        if(operations.isEmpty()) return new Operation();

        return operations.get(0);
    }
    
    public static List<Operation> findByCustomer(Customer customer){
        return operationDatabase.find(Operation.class, new HashMap<>(){{
            put("customer", customer);
        }});
    }
    
    public static boolean updateOperation(Operation operation){
        return operationDatabase.update(operation);
    }
    
    public static boolean deleteOperation(Operation operation){
        return operationDatabase.delete(operation);
    }
}