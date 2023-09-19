package com.luan.getlib.views;

import com.luan.getlib.controllers.BookController;
import com.luan.getlib.controllers.CustomerController;
import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Operation;
import com.luan.getlib.models.Result;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.InputReader;

import java.util.List;
import java.util.Map;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class CustomerApplicationView {
    private static final InputReader sc = new InputReader();
    private static final CustomerController customerController = new CustomerController();
    private static final BookController<Customer> bookController = new BookController<>();
    private static Map<String, Operation> myBooks;
    private static final char YES = 'y';

    public static void showMainMenu(Customer customer) {
        final int EXIT = 0, MY_ACCOUNT = 1, LIST_BOOKS = 2,
                  SEARCH_BOOKS = 3, MY_BOOKS = 4;
        while(true) {
            myBooks = bookController.getCustomerBooks(customer);
            if(customer.isEmpty())
                return;
            System.out.println("1-Minha Conta");
            System.out.println("2-Listar Livros");
            System.out.println("3-Buscar Livros");
            System.out.println("4-Meus Livros");
            System.out.println("0-Sair");

            int option = sc.getNextInt();
            switch(option) {
                case EXIT -> {
                    return;
                }
                case MY_ACCOUNT -> {
                   customer =  showAndEditAccountInfo(customer);
                }
                case LIST_BOOKS -> {
                    listBooks();
                }
                case SEARCH_BOOKS -> {
                    searchBooks(customer);
                }
                case MY_BOOKS -> {
                    System.out.println("Esta função está indisponível no momento!");
                    // showCustomerBooks();
                }
            }
        }
    }

    private static Customer showAndEditAccountInfo(Customer customer) {
        while (true) {
            final int RETURN = 0, RECHARGE = 1, CHANGE_ADDRESS = 2, CHANGE_EMAIL = 3, CHANGE_PHONE = 4, CHANGE_USERNAME = 5,
                    CHANGE_PASSWORD = 6, DELETE_ACCOUNT = 7;
            System.out.println(customer.getAccountInfo());
            System.out.println("1-Recarga de Créditos");
            System.out.println("2-Alterar Endereço");
            System.out.println("3-Alterar E-Mail");
            System.out.println("4-Alterar Telefone");
            System.out.println("5-Alterar Nome de Usuário");
            System.out.println("6-Alterar Senha");
            System.out.println("7-Excluir Conta");
            System.out.println("0-Voltar");

            int option = sc.getNextInt();
            switch (option) {
                case RETURN -> {
                    return customer;
                }
                case RECHARGE -> {
                    customer = makeRecharge(customer);
                }
                case CHANGE_ADDRESS -> {
                    customer = changeAddress(customer);
                }
                case CHANGE_EMAIL -> {
                    customer = changeEmail(customer);
                }
                case CHANGE_PHONE -> {
                    customer = changePhone(customer);
                }
                case CHANGE_USERNAME -> {
                    customer = changeUsername(customer);
                }
                case CHANGE_PASSWORD -> {
                    customer = changePassword(customer);
                }
                case DELETE_ACCOUNT -> {
                    return deleteAccount(customer);
                }
                default -> {
                    System.out.println("Opção Inválida! Tente Novamente");
                }
            }
        }
    }

    private static Customer makeRecharge(Customer customer) {
        System.out.print("Digite o Valor da Recarga: " + customer.getCurrency());
        Result<Customer> updateResult = customerController.makeRecharge(customer, sc.getNextDouble());
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer changeAddress(Customer customer) {
        List<String> address = CommonView.getAddressAndCurrency();
        Result<Customer> updateResult = customerController.changeAddressAndCurrency(customer, address);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer changeEmail(Customer customer) {
        System.out.print("Digite o novo E-Mail: ");
        Result<Customer> updateResult = customerController.changeEmail(customer, sc.getNextLine());
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer changePhone(Customer customer) {
        System.out.print("Digite o novo telefone: ");
        String phone = DataFormatter.removeNonNumbers(sc.getNextLine());
        Result<Customer> updateResult = customerController.changePhone(customer, phone);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer changeUsername(Customer customer) {
        System.out.print("Digite o novo nome de usuário: ");
        Result<Customer> updateResult = customerController.changeUsername(customer, sc.getNextLine());
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer changePassword(Customer customer) {
        System.out.print("Digite a nova senha: ");
        char[] firstPassword = sc.getPassword();
        System.out.print("Confirme a nova senha: ");
        char[] secondPassword = sc.getPassword();
        Result<Customer> updateResult = customerController.changePassword(customer, firstPassword, secondPassword);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static Customer deleteAccount(Customer customer) {
        System.out.println("Você está prestes a deletar sua conta! Deseja continuar? (y/n) ");
        char confirmation = Character.toLowerCase(sc.getNext());
        if(confirmation != YES){
            System.out.println("Operação cancelada!");
            return customer;
        }
        System.out.print("Digite sua senha para confirmar a exclusão da conta: ");
        char[] password = sc.getPassword();
        Result<Customer> updateResult = customerController.deleteCustomer(customer, password);
        System.out.println(updateResult.message);
        return updateResult.entity;
    }

    private static void listBooks() {
        CommonView.listBooks();
    }

    private static void searchBooks(Customer customer) {
        int bookId = CommonView.searchBooks();
        findBook(customer, bookId);
    }

    private static void findBook(Customer customer, int bookId) {
        Book book = CommonView.findBook(bookId);
        Result<Double> valueResult = bookController.getBookValue(customer, book);
        if(!valueResult.result) {
            System.out.println(valueResult.message);
            return;
        }
        double value = valueResult.entity;
        System.out.printf("Valor: %s%.2f", customer.getCurrency(), value);
        Operation operation = showBookOptions(customer, book);
        handleBookOptions(customer, operation, book, value, sc.getNextInt());
    }

    private static Operation showBookOptions(Customer customer, Book book) {
        Operation operation = new Operation();
        for(Operation value : myBooks.values()) {
            if(value.getType() == 'r') {
                operation = value;
                break;
            }
        }
        if(!operation.isEmpty()) {
            System.out.printf("1-Livro alugado em %s por %.2f / dia! Devolver");
        } else {
            System.out.println("1-Alugar");
        }
        System.out.println("2-Comprar");
        System.out.println("0-Voltar");
        return operation;
    }

    private static void handleBookOptions(Customer customer, Operation operation, Book book, double value, int option) {
        final int RETURN = 0, RENT = 1, PURCHASE = 2;
        switch(option) {
            case RETURN -> {
                return;
            }
            case RENT -> {
                Result<Operation> operationResult = bookController.rentOrReturnBook(customer, operation, book, value);
                System.out.println(operationResult.message);
            }
            case PURCHASE -> {
                Result<Operation> operationResult = bookController.purchaseOrReturnBook(customer, operation, book, value);
                System.out.println(operationResult.message);
            }
        }
    }
}
