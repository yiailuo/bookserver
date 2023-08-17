package com.book.manager.controller;


import com.book.manager.mapper.BookMapper;
import com.book.manager.mapper.BookTypeMapper;
import com.book.manager.model.Book;
import com.book.manager.model.BookPage;
import com.book.manager.model.BookType;
import com.book.manager.repository.BookRepository;
import com.book.manager.repository.BookTypeRepository;
import com.book.manager.service.BookService;
import com.book.manager.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookTypeRepository bookTypeRepository;
	@Autowired
	BookMapper bookMapper;
	@Autowired
	BookTypeMapper bookTypeMapper;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public BookPage<Book> find(@RequestBody Book query) {
		query.setIsPage(true);
		BookPage result = new BookPage();
		List<Book> bookList=bookMapper.findAll(query);
		result.setList(bookList);
		result.setTotal(bookMapper.count());
		int totalPage=bookMapper.count().intValue()/query.getSizePerPage();
		if(bookMapper.count().intValue()%query.getSizePerPage()>0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

	@RequestMapping(value = "typelist", method = RequestMethod.POST)
	public BookPage<BookType> typelist(@RequestBody BookType query) {
		query.setIsPage(true);
		BookPage result = new BookPage();
		List<BookType> bookList=bookTypeMapper.findAll(query);
		result.setList(bookList);
		result.setTotal(bookTypeMapper.count());
		int totalPage=bookTypeMapper.count().intValue()/query.getSizePerPage();
		if(bookTypeMapper.count().intValue()%query.getSizePerPage()>0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

	@RequestMapping(value = "getTypeList", method = RequestMethod.POST)
	public List<BookType> getTypeList() {
		return bookTypeRepository.findAll();
	}

	/**
	 * @function:添加图书
	 */
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST)
	public Book saveBook(@RequestBody Book dataset) {
		if(dataset.getId()==null){
			dataset.setCreatetime(DateUtil.getSimpleFormatedDateNow());
		}
		return bookService.saveOrUpdate(dataset);
	}

	/**
	 * @function:添加图书
	 */
	@RequestMapping(value = "/saveType", method = RequestMethod.POST)
	public BookType saveType(@RequestBody BookType dataset) {
		if(dataset.getId()==null){
			dataset.setCreatetime(DateUtil.getSimpleFormatedDateNow());
		}
		return bookTypeRepository.save(dataset);
	}

	@GetMapping({"detail/{id}"})
	public Book findDetail(@PathVariable("id") String id) {
		Book dataset = bookRepository.findById(id).orElse(new Book());
		return dataset;
	}

	@GetMapping({"typeDetail/{id}"})
	public BookType typeDetail(@PathVariable("id") String id) {
		BookType dataset = bookTypeRepository.findById(id).orElse(new BookType());
		return dataset;
	}

	/**
	 * 检查分类名是否存在
	 *
	 */
	@RequestMapping(value="/checkType",method=RequestMethod.POST)
	public JSONObject checkType(@RequestBody BookType dataset) {
		JSONObject jo = new JSONObject();
		List<BookType> types = bookTypeRepository.findByName(dataset.getName());
		if(types.size()>0&&!types.get(0).getId().equals(dataset.getId())){
			jo.put("success",false);
		}else{
			jo.put("success",true);
		}
		return jo;
	}

	/**
	 * 检查分类下是否有图书存在
	 *
	 */
	@RequestMapping(value="/checkBook",method=RequestMethod.POST)
	public JSONObject checkBook(@RequestBody BookType dataset) {
		JSONObject jo = new JSONObject();
		List<Book> books = bookRepository.findByTypeid(dataset.getId());
		if(books.size()>0){
			jo.put("success",false);
		}else{
			jo.put("success",true);
		}
		return jo;
	}

	/**
	 * 删除图书
	 *
	 */
	@RequestMapping(value="/deleteBook",method=RequestMethod.POST)
	public JSONObject deleteBook(@RequestBody Book dataset) {
		JSONObject jo = new JSONObject();
		bookService.delete(dataset);
		jo.put("success",true);
		return jo;
	}

	/**
	 * 删除分类
	 *
	 */
	@RequestMapping(value="/deleteType",method=RequestMethod.POST)
	public JSONObject deleteType(@RequestBody BookType dataset) {
		JSONObject jo = new JSONObject();
		bookTypeRepository.delete(dataset);
		jo.put("success",true);
		return jo;
	}

	@RequestMapping(value = "/importBook", method = RequestMethod.POST)
	public JSONArray importBook(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		JSONArray infos=bookService.importBook(file);
		return infos;
	}

}
