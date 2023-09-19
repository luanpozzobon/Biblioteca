package com.luan.getlib.interfaces;


import com.luan.getlib.models.*;
import com.luan.getlib.repository.BookRepository;
import com.luan.getlib.repository.CustomerRepository;
import com.luan.getlib.repository.EmployeeRepository;
import com.luan.getlib.repository.OperationRepository;
import com.luan.getlib.security.PasswordUtils;
import com.luan.getlib.utils.DataValidator;
import com.luan.getlib.utils.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @since v0.2.0
 * @author luanpozzobon
 */
public class MenuScreen {
    private static char[] salt, password;
    private static String title, genre;
    private static int amount, parentalRating;
    private static double value;
    private static Book book;
    private static char exit;
    private static Map<String, Operation> myBooks;
    
    
    public static void employee(Employee emp, InputReader sc){
        System.out.println("Bem-Vindo " + emp.getFullName());
        while(true){
            exit = 'n';
            System.out.println("Selecione uma opção:");
            System.out.println("1-Editar cadastro");
            System.out.println("2-Listar livros");
            System.out.println("3-Buscar livros");
            System.out.println("0-Sair");

            switch(sc.getNextInt()){
                case 0:
                    return;
                case 1:
                    System.out.println("1-Alterar senha");
                    System.out.println("2-Excluir conta");
                    System.out.println("0-Sair");
                    switch(sc.getNextInt()){
                        case 0:
                            break;
                        case 1:
                            System.out.print("Nova senha: ");
                            password = sc.getPassword();
                            System.out.print("Confirme a senha: ");
                            while (!DataValidator.isPasswordValid(new String(password), new String(sc.getPassword())).result){
                                System.out.println("Tente novamente!");
                                System.out.print("Nova senha: ");
                                password = sc.getPassword();
                                System.out.print("Confirme a senha: ");
                            }

                            emp.setSalt(salt = PasswordUtils.generateSalt());
                            emp.setPassword(password = PasswordUtils.encryptPassword(password, salt));

                            if(EmployeeRepository.updateEmployee(emp)){
                                System.out.println("Senha alterada com sucesso!");
                            } else {
                                System.out.println("Ocorreu um erro ao salvar a nova senha. Tente novamente mais tarde!");
                            }

                            Arrays.fill(salt, ' ');
                            Arrays.fill(password, ' ');
                            break;
                        case 2:
                            System.out.print("Deseja realmente excluir sua conta? (y/n) ");
                            switch(sc.getNext()){
                                case 'y':
                                    System.out.print("Digite sua senha para confirmar a operação: ");
                                    if(DataValidator.arePasswordsEqual(new String(PasswordUtils.encryptPassword(sc.getPassword(), emp.getSalt())), new String(emp.getPassword()))){
                                        if(EmployeeRepository.deleteEmployee(emp)){
                                            System.out.println("Cadastro excluído com sucesso!");
                                            return;
                                        }
                                    } else {
                                        System.out.println("Senha incorreta");
                                    }
                                    break;
                                case 'n':
                                    break;
                            }
                    }
                    break;

                case 2:
                    listBooks();
                    System.out.println("Pressione [ENTER] para voltar!");
                    sc.getNextLine();
                    break;
                case 3:
                    System.out.print("Digite o nome do livro: ");
                    title = sc.getNextLine();
                    if(!listBooks(title)) {
                        System.out.println("A busca não encontrou nenhuma correspondência!");
                        System.out.print("Deseja adicionar um novo livro? (y/n) ");
                        switch(sc.getNext()){
                            case 'y':
                                System.out.print("Título: ");
                                title = sc.getNextLine();
                                System.out.print("Gênero: ");
                                genre = sc.getNextLine();
                                System.out.print("Classificação Indicativa: ");
                                parentalRating = sc.getNextInt();
                                System.out.print("Quantidade: ");
                                amount = sc.getNextInt();
                                System.out.print("Valor: $");
                                value = sc.getNextDouble();

                                book = new Book(title, genre, amount, value, parentalRating);
                                if(BookRepository.saveBook(book)){
                                    System.out.println("Livro salvo com sucesso!");
                                } else {
                                    System.out.println("Falha no salvamento do livro! Tente novamente mais tarde!");
                                }
                        }
                        break;
                    }
                    System.out.print("Digite o Id do livro desejado: ");
                    book = BookRepository.findById(sc.getNextInt());

                    do{
                        System.out.println("\nId: " + book.getId());
                        System.out.println("Título: " + book.getTitle());
                        System.out.println("Gênero: " + book.getGenre());
                        System.out.println("Classificação Indicativa: " + book.getParentalRating());
                        System.out.println("Quantidade: " + book.getAmount());
                        System.out.println("Valor: $" + book.getValue() + "\n");

                        System.out.println("1-Inserir");
                        System.out.println("2-Remover");
                        System.out.println("3-Editar preço");
                        System.out.println("0-Voltar");
                        switch(sc.getNextInt()){
                            case 0:
                                exit = 'y';
                                break;
                            case 1:
                                System.out.println("Quantidade anterior: " + book.getAmount());
                                System.out.print("Quantidade a inserir: ");
                                amount = sc.getNextInt();
                                System.out.println("Nova quantidade: " + (book.getAmount() + amount));
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setAmount(book.getAmount() + amount);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }

                                break;
                            case 2:
                                System.out.println("Quantidade anterior: " + book.getAmount());
                                System.out.print("Quantidade a remover: ");
                                amount = sc.getNextInt();
                                System.out.println("Livros restantes: " + (book.getAmount() - amount));
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setAmount(book.getAmount() - amount);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }
                                break;
                            case 3:
                                System.out.println("Valor anterior: $" + book.getValue());
                                System.out.print("Novo Valor: $");
                                value = sc.getNextDouble();
                                System.out.print("Confirma a operação? (y/n) ");
                                switch(sc.getNext()){
                                    case 'y':
                                        book.setValue(value);
                                        updateBook();
                                        break;
                                    case 'n':
                                        continue;
                                    default:
                                        System.out.println("Opção Inválida!");
                                }
                        }
                    } while(exit != 'y');
            }            
        }
    }
        
    @Deprecated
    private static void listBooks(){
        List<Book> books = BookRepository.getAllBooks();
        for(Book item : books){
            System.out.println("\nId: " + item.getId());
            System.out.println("Título: " + item.getTitle());
        }
    }

    @Deprecated
    private static boolean listBooks(String title){
        List<Book> books = new ArrayList<>();
        if((books = BookRepository.findByTitle(title)) != null){
            for(Book item : books){
                System.out.println("\nId: " + item.getId());
                System.out.println("Título: " + item.getTitle());
            }
            
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    private static void listBooks(Customer cst){
        if((myBooks = OperationRepository.findByCustomerId(cst)) != null){
            for(String key : myBooks.keySet()){
                System.out.println("\nId: " + myBooks.get(key).getBook().getId());
                System.out.println("Título: " + key);
                System.out.println("Tipo: " + myBooks.get(key).getTypeAsString());
            }
        }
    }

    @Deprecated
    private static void updateBook(){
        if(BookRepository.updateBook(book)){
            System.out.println("Operação concluída com sucesso!");
        } else {
            System.out.println("Ocorreu um erro ao alterar o livro: " + book.getTitle() + "! Tente novamente mais tarde!");
        }
    }

    @Deprecated
    private static boolean updateCustomer(Customer cst){
        if(CustomerRepository.updateCustomer(cst)){
            System.out.println("Operação concluída com sucesso!");
            return true;
        } else {
            System.out.println("Occoreu um erro ao alterar o cadastro! Tente novamente mais tarde!");
            return false;
        }
    }
}