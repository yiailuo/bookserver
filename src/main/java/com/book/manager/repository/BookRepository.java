package com.book.manager.repository;

import com.book.manager.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookRepository extends JpaRepository<Book,String> {
    List<Book> findByTypeid(String typeid);
}
