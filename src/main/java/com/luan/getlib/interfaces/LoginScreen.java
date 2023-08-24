package com.luan.getlib.interfaces;

import com.luan.getlib.dao.CustomerDAO;
import com.luan.getlib.dao.EmployeeDAO;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Employee;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;

/**
 * @since v0.1.2
 * @author luanpozzobon
 */
public class LoginScreen {
    private static final InputReader sc = new InputReader();
    
    public static Employee employee(){
        while(true){
            System.out.print("Digite seu código de acesso: ");
            String accessCode = sc.getNextLine();
            Employee emp = EmployeeDAO.findByAccessCode(accessCode);
            if(emp == null){
                System.out.println("Não foi possível encontrar o código de acesso digitado!");
                System.out.println("Verifique se o código de acesso foi digitado corretamente e tente novamente");
                continue;
            }
            System.out.print("Digite sua senha: ");
            String password = PasswordUtils.encryptPassword(sc.getNextLine(), emp.getSalt());
            if(!DataValidator.arePasswordsEqual(emp.getPassword(), password)){
                System.out.println("Senha Incorreta!");
                continue;
            }
            return emp;
        }
    }
    
    public static Customer customer(){
        while(true){
            System.out.print("Digite seu nome de usuário: ");
            String username = sc.getNextLine();
            Customer cst = CustomerDAO.findByUsername(username);
            if(cst == null){
                System.out.println("Não foi possível encontrar o nome de usuário digitado!");
                System.out.println("Verifique se o nome de usuário foi digitado corretamente e tente novamente");
                continue;
            }
            cst = CustomerDAO.findById(cst.getId());
            System.out.print("Digite sua senha: ");
            String password = PasswordUtils.encryptPassword(sc.getNextLine(), cst.getSalt());
            if(!DataValidator.arePasswordsEqual(cst.getPassword(), password)){
                System.out.println("Senha Incorreta!");
                continue;
            }
            return cst;
        }
    }
}
