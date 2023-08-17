package com.book.manager.repository;

import com.book.manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByUsernameAndStatus(String username, String status);
    List<User> findByUsername(String username);
    List<User> findByCard(String card);
}
