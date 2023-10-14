package com.luan.getlib.repositories;

import com.luan.getlib.models.Book;
import com.luan.getlib.models.Operation;
import com.luan.getlib.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findAllByUser(User user);
    boolean existsByUserAndBook(User user, Book book);
    Operation findByUserAndBook(User user, Book book);
}
