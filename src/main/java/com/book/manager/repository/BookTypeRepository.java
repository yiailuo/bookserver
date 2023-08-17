package com.book.manager.repository;

import com.book.manager.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookTypeRepository extends JpaRepository<BookType,String> {
    List<BookType> findByName(String name);
}
