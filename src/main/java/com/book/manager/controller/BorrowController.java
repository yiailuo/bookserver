package com.book.manager.controller;


import com.book.manager.mapper.BorrowMapper;
import com.book.manager.model.Book;
import com.book.manager.model.BookPage;
import com.book.manager.model.Borrow;
import com.book.manager.model.User;
import com.book.manager.repository.BookRepository;
import com.book.manager.repository.BorrowRepository;
import com.book.manager.repository.UserRepository;
import com.book.manager.utils.DateUtil;
import com.book.manager.utils.MyPasswordEncoder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/borrow")
public class BorrowController {

	@Autowired
	BorrowRepository borrowRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BorrowMapper borrowMapper;
	@Value("${init.password}")
	private String password;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public BookPage<Borrow> find(@RequestBody Borrow query) {
		query.setIsPage(true);
		BookPage result = new BookPage();
		List<Borrow> bookSelfList=borrowMapper.findAll(query);
		result.setList(bookSelfList);
		result.setTotal(borrowMapper.count());
		int totalPage=borrowMapper.count().intValue()/query.getSizePerPage();
		if(borrowMapper.count().intValue()%query.getSizePerPage()>0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

	/**
	 * @function:添加
	 */
	@RequestMapping(value = "/saveBorrow", method = RequestMethod.POST)
	public Borrow saveBorrow(@RequestBody Borrow dataset) {
		if(dataset.getId()==null){
			dataset.setBorrowdate(DateUtil.getSimpleFormatedDateNow());
		}
		List<User> users = userRepository.findByCard(dataset.getCard());
		if(users.size()==0){
			User user = new User();
			user.setStatus("1");
			user.setRole("读者");
			user.setUsername(dataset.getCard());
			user.setPassword(new MyPasswordEncoder().encode(password));
			user.setRealname(dataset.getUsername());
			user.setCard(dataset.getCard());
			user.setSex(dataset.getSex());
			user.setAge(dataset.getAge());
			user.setPhone(dataset.getPhone());
			User user1 = userRepository.save(user);
			dataset.setUserid(user1.getId());
		}else{
			User user = users.get(0);
			user.setUsername(dataset.getCard());
			user.setRealname(dataset.getUsername());
			user.setCard(dataset.getCard());
			user.setSex(dataset.getSex());
			user.setAge(dataset.getAge());
			user.setPhone(dataset.getPhone());
			userRepository.save(user);
			dataset.setUserid(users.get(0).getId());
		}
		return borrowRepository.save(dataset);
	}


	@GetMapping({"detail/{id}"})
	public Borrow findDetail(@PathVariable("id") String id) {
		Borrow dataset = borrowRepository.findById(id).orElse(new Borrow());
		User user = userRepository.findById(dataset.getUserid()).orElse(new User());
		Book book = bookRepository.findById(dataset.getBookid()).orElse(new Book());
		dataset.setCard(user.getCard());
		dataset.setPhone(user.getPhone());
		dataset.setUsername(user.getRealname());
		dataset.setBookname(book.getName());
		return dataset;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteBorrow",method=RequestMethod.POST)
	public JSONObject deleteBorrow(@RequestBody Borrow dataset) {
		JSONObject jo = new JSONObject();
		borrowRepository.delete(dataset);
		jo.put("success",true);
		return jo;
	}

}
