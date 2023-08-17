package com.book.manager.mapper;


import com.book.manager.model.BookType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookTypeMapper {

	List<BookType> findAll(BookType query);

	public Long count();

}
