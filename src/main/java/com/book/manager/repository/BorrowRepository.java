package com.book.manager.repository;

import com.book.manager.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface BorrowRepository extends JpaRepository<Borrow,String> {
}
