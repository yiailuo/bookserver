package com.book.manager.repository;

import com.book.manager.model.BookSelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookSelfRepository extends JpaRepository<BookSelf,String> {
    List<BookSelf> findByName(String name);
}
