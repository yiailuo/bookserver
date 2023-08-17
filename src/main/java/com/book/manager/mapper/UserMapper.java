package com.book.manager.mapper;


import com.book.manager.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMapper {

	List<User> findAll(User query);

	public Long count();

}
