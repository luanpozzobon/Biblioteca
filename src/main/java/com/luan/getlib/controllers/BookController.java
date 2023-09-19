package com.luan.getlib.controllers;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Customer;
import com.luan.getlib.models.Operation;
import com.luan.getlib.models.Result;
import com.luan.getlib.repository.BookRepository;
import com.luan.getlib.repository.OperationRepository;
import com.luan.getlib.services.CurrencyService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookController<T> {
    private final String NO_BOOKS_FOUND = "Não foi encontrado nenhum livro!";
    private final String BOOKS_FOUND = "Livros encontrados";

    public Map<String, Operation> getCustomerBooks(Customer customer) {
        return OperationRepository.findByCustomerId(customer);
    }

    public Result<List<Book>> list() {
        List<Book> bookList = BookRepository.getAllBooks();
        if(bookList.isEmpty())
            return new Result<>(false, NO_BOOKS_FOUND, bookList);
        return new Result<>(true, BOOKS_FOUND, bookList);
    }

    public Result<List<Book>> search(String title) {
        List<Book> bookList = BookRepository.findByTitle(title);
        if(bookList.isEmpty())
            return new Result<>(false, NO_BOOKS_FOUND, bookList);
        return new Result<>(true, BOOKS_FOUND, bookList);
    }

    public Result<Book> search(int bookId) {
        Book book = BookRepository.findById(bookId);
        if(book.isEmpty())
            return new Result<>(false, NO_BOOKS_FOUND, book);
        return new Result<>(true, BOOKS_FOUND, book);
    }

    public Result<Double> getBookValue(T entity, Book book) {
        final String ORIGINAL_VALUE = "Valor original!";
        if(entity instanceof Customer)
            return getConvertedBookValue((Customer) entity, book);
        return new Result<>(true, ORIGINAL_VALUE, book.getValue());
    }

    private Result<Double> getConvertedBookValue(Customer customer, Book book) {
        final String CONVERTED_VALUE = "Valor convertido!";
        final String EMPTY_CUSTOMER = "Não foi possível encontrar o cliente!";
        final String EMPTY_CURRENCY = "Não foi possível encontrar a moeda do cliente!";
        final String CONVERTION_ERROR = "Não foi possível converter o valor do livro!";
        if(customer.isEmpty())
            return new Result<>(false, EMPTY_CUSTOMER, 0.0);
        final String currency = customer.getCurrency();
        if(currency.isEmpty())
            return new Result<>(false, EMPTY_CURRENCY, 0.0);
        final CurrencyService valueConverter = new CurrencyService();
        final double convertedValue = valueConverter.convertFromUSD(book.getValue(), currency);
        if(convertedValue <= 0.0)
            return new Result<>(false, CONVERTION_ERROR, 0.0);
        return new Result<>(true, CONVERTED_VALUE, convertedValue);
    }

    public Map<String, Operation> checkThisBook(Book book, Map<String, Operation> myBooks) {
        return myBooks.entrySet()
                .stream()
                .filter(map -> map.getKey().equals(book.getTitle()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Result<Operation> rentOrReturnBook(Customer customer, Operation operation, Book book, double value) {
        final String RENT_ERROR = "Não foi possível alugar o livro!", RENT_SUCCESS = "Livro alugado com sucesso!";
        final String RETURN_ERROR = "Não foi possível devolver o livro!", RETURN_SUCCESS = "Livro devolvido com sucesso!";
        if(operation.isEmpty()) {
            operation = new Operation('r', book, customer, LocalDate.now(), value);
            if(!OperationRepository.saveOperation(operation))
                return new Result<>(false, RENT_ERROR, operation);
            return new Result<>(true, RENT_SUCCESS, operation);
        } else {
            if(!OperationRepository.deleteOperation(operation))
                return new Result<>(false, RETURN_ERROR, operation);
            return new Result<>(true, RENT_SUCCESS, operation);
        }
    }

    public Result<Operation> purchaseOrReturnBook(Customer customer, Operation operation, Book book, double value) {
        final String PURCHASE_ERROR = "Não foi possível comprar o livro!",
                     PURCHASE_SUCCESS = "Livro comprado com sucesso!";
        final String RETURN_ERROR = "Não foi possível devolver o livro!",
                     RETURN_NOT_AVAILABLE = "O livro foi comprado a mais de 7 dias!",
                     RETURN_SUCCESS = "Livro devolvido com sucesso!";
        if(operation.isEmpty()) {
            operation = new Operation('p', book, customer, LocalDate.now(), value);
            if(!OperationRepository.saveOperation(operation))
                return new Result<>(false, PURCHASE_ERROR, operation);
            return new Result<>(true, PURCHASE_SUCCESS, operation);
        } else {
            if(Period.between(operation.getOperationDate(), LocalDate.now()).getDays() > 7)
                return new Result<>(false, RETURN_NOT_AVAILABLE, operation);
            if(!OperationRepository.deleteOperation(operation))
                return new Result<>(false, RETURN_ERROR, operation);
            return new Result<>(true, RETURN_SUCCESS, operation);
        }
    }
}