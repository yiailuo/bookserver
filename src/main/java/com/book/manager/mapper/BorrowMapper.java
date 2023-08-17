package com.book.manager.mapper;


import com.book.manager.model.Borrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowMapper {

	List<Borrow> findAll(Borrow query);

	public Long count();

}
