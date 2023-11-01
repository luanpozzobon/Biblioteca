package com.luan.getlib.views;

import com.luan.getlib.controllers.AddressController;
import com.luan.getlib.controllers.BookController;
import com.luan.getlib.models.Address;
import com.luan.getlib.models.Book;
import com.luan.getlib.models.Result;
import com.luan.getlib.utils.DataFormatter;
import com.luan.getlib.utils.InputReader;

import java.util.ArrayList;
import java.util.List;

public class CommonView {
    private static final InputReader sc = new InputReader();
    private static final BookController bookController = new BookController();

    public static List<String> getAddressAndCurrency() {
        System.out.print("CEP: ");
        String zipCode = DataFormatter.removeNonNumbers(sc.getNextLine());
        final AddressController addressController = new AddressController();
        final Result<Address> addressResult = addressController.findAddressByZipCode(zipCode);
        Address address = addressResult.entity;
        if(!addressResult.result){
            System.out.println(addressResult.message);
            address.setZipCode(zipCode);
            System.out.print("País: ");
            address.setNation(sc.getNextLine());
            System.out.print("Estado: ");
            address.setState(sc.getNextLine());
            System.out.print("Cidade: ");
            address.setCity(sc.getNextLine());
        }
        final Result<String> currencyResult = addressController.findAddressByCountry(address.getNation());
        String currency;
        if(!currencyResult.result) {
            System.out.println(currencyResult.message);
            System.out.println("Digite a moeda do país (ex. BRL, USD): ");
            currency = sc.getNextLine();
        } else {
            currency = currencyResult.entity;
        }
        System.out.print("Rua: ");
        address.setStreet(sc.getNextLine());
        System.out.print("Número: ");
        address.setNumber(sc.getNextLine());
        return new ArrayList<>(){{
            add(address.toString());
            add(currency);
        }};
    }

    public static void listBooks() {
        Result<List<Book>> bookResult = bookController.list();
        if(!bookResult.result)
            System.out.println(bookResult.message);
        for(Book book : bookResult.entity)
            System.out.println(book.getBasicInfo());
        System.out.println("Pressione [ENTER] para voltar!");
        sc.getNextLine();
    }

    public static int searchBooks() {
        System.out.print("Digite o título a procurar: ");
        Result<List<Book>> bookResult = bookController.search(sc.getNextLine());
        if(!bookResult.result)
            System.out.println(bookResult.message);
        for(Book book : bookResult.entity)
            System.out.println(book.getBasicInfo());
        System.out.print("Digite o id do livro ou 0 p/ sair: ");
        return sc.getNextInt();
    }

    public static Book findBook(int bookId){
        if(bookId < 1)
            return new Book();
        Result<Book> bookResult = bookController.search(bookId);
        if(!bookResult.result)
            System.out.println(bookResult.message);
        Book book = bookResult.entity;
        System.out.println(bookResult.entity.getFullInfo());
        return book;
    }
}
