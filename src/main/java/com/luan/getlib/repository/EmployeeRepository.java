package com.luan.getlib.repository;

import com.luan.getlib.models.Employee;

import java.util.HashMap;
import java.util.List;

/**
 * @since v1.2.0
 * @author luanpozzobon
 */
public class EmployeeRepository {

    private static final DatabaseAccess<Employee> employeeDatabase = new DatabaseAccess<>();
    public static boolean saveEmployee(Employee employee){
        return employeeDatabase.save(employee);
    }
    
    public static Employee findByAccessCode(String accessCode){
        List<Employee> employees = employeeDatabase.find(Employee.class, new HashMap<>(){{
            put("accessCode", accessCode);
        }});
        if(employees.isEmpty()) return new Employee();

        return employees.get(0);
    }
    
    public static boolean accessCodeExists(String accessCode){
        return findByAccessCode(accessCode) != null;
    }
    
    public static boolean updateEmployee(Employee employee){
        return employeeDatabase.update(employee);
    }
    
    public static boolean deleteEmployee(Employee employee){
        return employeeDatabase.delete(employee);
    }
}
