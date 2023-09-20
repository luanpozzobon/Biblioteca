package com.luan.getlib.views;

import com.luan.getlib.controllers.BookController;
import com.luan.getlib.controllers.EmployeeController;
import com.luan.getlib.models.*;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.InputReader;

/**
 * @since v1.3.1
 * @author luanpozzobon
 */
public class EmployeeApplicationView {
    private static final InputReader sc = new InputReader();
    private static final EmployeeController employeeController = new EmployeeController();
    private static final BookController<Employee> bookController = new BookController();

    public static void showMainMenu(Employee employee) {
        final int EXIT = 0, MY_ACCOUNT = 1, LIST_BOOKS = 2, SEARCH_BOOKS = 3;
        while (true) {
            if (employee.isEmpty())
                return;
            System.out.println("1-Minha Conta");
            System.out.println("2-Listar Livros");
            System.out.println("3-Buscar Livros");
            System.out.println("0-Sair");
            int option = sc.getNextInt();
            switch (option) {
                case EXIT -> {
                    return;
                }
                case MY_ACCOUNT -> {
                    employee = showAndEditAccountInfo(employee);
                }
                case LIST_BOOKS -> {
                    listBooks();
                }
                case SEARCH_BOOKS -> {
                    searchBooks(employee);
                }
            }
        }
    }

    private static Employee showAndEditAccountInfo(Employee employee) {
        final int RETURN = 0, CHANGE_EMAIL = 1, CHANGE_PHONE = 2,
                  CHANGE_PASSWORD = 3, DELETE_ACCOUNT = 4;
        while(true) {
            System.out.println(employee.getAccountInfo());
            System.out.println("1-Alterar E-Mail");
            System.out.println("2-Alterar telefone");
            System.out.println("3-Alterar Senha");
            System.out.println("4-Excluir Conta");
            System.out.println("0-Voltar");
            int option = sc.getNextInt();
            switch(option) {
                case RETURN -> {
                    return employee;
                }
                case CHANGE_EMAIL -> {
                    return changeEmail(employee);
                }
                case CHANGE_PHONE -> {
                    return changePhone(employee);
                }
                case CHANGE_PASSWORD -> {
                    return changePassword(employee);
                }
                case DELETE_ACCOUNT -> {
                    return deleteAccount(employee);
                }
            }
        }
    }

    private static Employee changeEmail(Employee employee) {
        System.out.print("Digite o novo E-Mail: ");
        Result<Employee> updateResult = employeeController.changeEmail(employee, sc.getNextLine());
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Employee changePhone(Employee employee) {
        System.out.print("Digite o novo telefone: ");
        String phone = DataFormatter.removeNonNumbers(sc.getNextLine());
        Result<Employee> updateResult = employeeController.changePhone(employee, phone);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Employee changePassword(Employee employee) {
        System.out.print("Digite a nova senha: ");
        char[] firstPassword = sc.getPassword();
        System.out.print("Confirme a nova senha: ");
        char[] secondPassword = sc.getPassword();
        Result<Employee> updateResult = employeeController.changePassword(employee, firstPassword, secondPassword);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Employee deleteAccount(Employee employee) {
        final char YES = 'y';
        System.out.println("Você está prestes a deletar sua conta! Deseja continuar? (y/n) ");
        char confirmation = Character.toLowerCase(sc.getNext());
        if(confirmation != YES){
            System.out.println("Operação cancelada!");
            return employee;
        }
        System.out.print("Digite sua senha para confirmar a exclusão da conta: ");
        char[] password = sc.getPassword();
        Result<Employee> updateResult = employeeController.deleteCustomer(employee, password);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static void listBooks() {
        CommonView.listBooks();
    }

    private static void searchBooks(Employee employee) {
        int bookId = CommonView.searchBooks();
        findBook(employee, bookId);
    }

    private static void findBook(Employee employee, int bookId) {
        Book book = CommonView.findBook(bookId);
        Result<Double> valueResult = bookController.getBookValue(employee, book);
        if(!valueResult.result) {
            System.out.println(valueResult.message);
            return;
        }
        double value = valueResult.entity;
        System.out.printf("Valor: USD%.2f", value);
        showBookOptions();
        handleBookOptions(book);
    }

    private static void showBookOptions() {
        System.out.println("1-Inserir");
        System.out.println("2-Remover");
        System.out.println("3-Editar preço");
        System.out.println("0-Voltar");
    }

    private static void handleBookOptions(Book book) {
        final int RETURN = 0, INSERT = 1, REMOVE = 2, CHANGE_VALUE = 3;
        int option = sc.getNextInt(), amount;
        Result<Book> bookResult;
        switch(option) {
            case RETURN -> {
                return;
            }
            case INSERT -> {
                System.out.println("Quantidade de livros anterior: " + book.getAmount());
                System.out.print("Quantidade de livros a inserir: ");
                amount = sc.getNextInt();
                bookResult = bookController.insertBook(book, amount);
                System.out.println(bookResult.message);
            }
            case REMOVE -> {
                System.out.println("Quantidade de livros anterior: " + book.getAmount());
                System.out.print("Quantidade de livros a remover: ");
                amount = sc.getNextInt();
                bookResult = bookController.removeBook(book, amount);
                System.out.println(bookResult.message);
            }
            case CHANGE_VALUE -> {
                System.out.println("Valor anterior do livro: USD" + book.getValue());
                System.out.print("Novo valor do livro: USD");
                double value = sc.getNextDouble();
                bookResult = bookController.changeBookValue(book, value);
                System.out.println(bookResult.message);
            }
        }
    }
}