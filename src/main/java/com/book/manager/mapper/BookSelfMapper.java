package com.book.manager.mapper;


import com.book.manager.model.BookSelf;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookSelfMapper {

	List<BookSelf> findAll(BookSelf query);

	public Long count();

}
