package com.book.manager.mapper;


import com.book.manager.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookMapper {

	List<Book> findAll(Book query);

	public Long count();

}
