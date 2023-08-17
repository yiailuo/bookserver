package com.book.manager.controller;


import com.book.manager.mapper.BookSelfMapper;
import com.book.manager.model.BookPage;
import com.book.manager.model.BookSelf;
import com.book.manager.repository.BookSelfRepository;
import com.book.manager.service.BookSelfService;
import com.book.manager.utils.JwtTokenUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/bookself")
public class BookSelfController {

	@Autowired
	BookSelfService bookSelfService;
	@Autowired
	BookSelfRepository bookSelfRepository;
	@Autowired
	BookSelfMapper bookSelfMapper;
	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public BookPage<BookSelf> find(@RequestBody BookSelf query) {
		query.setIsPage(true);
		BookPage result = new BookPage();
		List<BookSelf> bookSelfList=bookSelfMapper.findAll(query);
		result.setList(bookSelfList);
		result.setTotal(bookSelfMapper.count());
		int totalPage=bookSelfMapper.count().intValue()/query.getSizePerPage();
		if(bookSelfMapper.count().intValue()%query.getSizePerPage()>0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

	@RequestMapping(value = "getBookSelfList", method = RequestMethod.POST)
	public List<BookSelf> getBookSelfList() {
		return bookSelfRepository.findAll();
	}

	/**
	 * @function:添加新用户
	 */
	@RequestMapping(value = "/saveBookSelf", method = RequestMethod.POST)
	public BookSelf saveBookSelf(@RequestBody BookSelf dataset) {
		return bookSelfService.saveOrUpdate(dataset);
	}


	@GetMapping({"detail/{id}"})
	public BookSelf findDetail(@PathVariable("id") String id) {
		BookSelf dataset = bookSelfRepository.findById(id).orElse(new BookSelf());
		return dataset;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteBookSelf",method=RequestMethod.POST)
	public JSONObject deleteBookSelf(@RequestBody BookSelf dataset) {
		JSONObject jo = new JSONObject();
		bookSelfService.delete(dataset);
		jo.put("success",true);
		return jo;
	}

	/**
	 * 检查书架号是否存在
	 */
	@RequestMapping(value="/checkBookSelf",method=RequestMethod.POST)
	public JSONObject checkBookSelf(@RequestBody BookSelf dataset) {
		JSONObject jo = new JSONObject();
		List<BookSelf> bookSelf = bookSelfRepository.findByName(dataset.getName());
		if(bookSelf.size()>0&&!bookSelf.get(0).getId().equals(dataset.getId())){
			jo.put("success",false);
		}else{
			jo.put("success",true);
		}
		return jo;
	}

	@RequestMapping(value = "/importBookSelf", method = RequestMethod.POST)
	public JSONArray importBookSelf(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		JSONArray infos=bookSelfService.importBookSelf(file);
		return infos;
	}

}
