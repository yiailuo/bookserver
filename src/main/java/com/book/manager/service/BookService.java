package com.book.manager.service;

import com.book.manager.model.Book;
import com.book.manager.model.BookSelf;
import com.book.manager.model.BookType;
import com.book.manager.repository.BookRepository;
import com.book.manager.repository.BookSelfRepository;
import com.book.manager.repository.BookTypeRepository;
import com.book.manager.utils.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookTypeRepository bookTypeRepository;
	@Autowired
	BookSelfRepository bookSelfRepository;

	@Transactional
	public Book saveOrUpdate(Book zb) {
		zb.setCreatetime(DateUtil.getSimpleFormatedDateNow());
		return bookRepository.save(zb);
	}

	public void delete(Book dataset){
		bookRepository.delete(dataset);
	}

	@Transactional
	public JSONArray importBook(MultipartFile file) throws IOException {
		JSONArray objects = new JSONArray();
		JSONObject jsonObject = null;
		// 创建输入工作流
		InputStream is = null;
		is = file.getInputStream();
		// 获取文件名
		String fileName = file.getOriginalFilename();
		Workbook wb = null;
		// 根据文件名判断文件是2003版本还是2007版本
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception ex) {
			wb = new HSSFWorkbook(is);
		}
		Sheet sheet=wb.getSheetAt(0);
		List<Book> bookList=new ArrayList<>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Book book=new Book();
			if(row.getCell(0)==null|| StringUtils.isEmpty(row.getCell(0).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "书名不能为空");
				objects.add(jsonObject);
			}
			if(row.getCell(1)==null|| StringUtils.isEmpty(row.getCell(1).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "所属分类不能为空");
				objects.add(jsonObject);
			}else{
				List<BookType> bookTypes = bookTypeRepository.findByName(row.getCell(1).toString());
				if(bookTypes.size()==0){
					BookType bookType = new BookType();
					bookType.setName(row.getCell(1).toString());
					BookType type = bookTypeRepository.save(bookType);
					book.setTypeid(type.getId());
				}else{
					book.setTypeid(bookTypes.get(0).getId());
				}
			}
			if(row.getCell(2)==null|| StringUtils.isEmpty(row.getCell(2).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "书架号不能为空");
				objects.add(jsonObject);
			}else{
				List<BookSelf> bookSelfs = bookSelfRepository.findByName(row.getCell(2).toString());
				if(bookSelfs.size()==0){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "书架号不存在，请先到系统管理中新增书架");
					objects.add(jsonObject);
				}else{
					if(row.getCell(7)==null|| StringUtils.isEmpty(row.getCell(7).toString())){
						jsonObject = new JSONObject(); // 存放错误提示
						jsonObject.put("index", i + 1);
						jsonObject.put("message", "数量必须是大于0的整数");
						objects.add(jsonObject);
					}else{
						BookSelf bookSelf = bookSelfs.get(0);
						int stock = Integer.parseInt(row.getCell(7).toString().replace(".0", ""));
						if(bookSelf.getCapacity()<stock){
							jsonObject = new JSONObject(); // 存放错误提示
							jsonObject.put("index", i + 1);
							jsonObject.put("message", "入库数量"+stock+"大于书架号剩余容量"+bookSelf.getCapacity()+"，请修改数量后重试");
							objects.add(jsonObject);
						}else{
							bookSelf.setUses(bookSelf.getUses()+stock);
							bookSelf.setCapacity(bookSelf.getCapacity()-stock);
							bookSelfRepository.save(bookSelf);
							book.setBookselfid(bookSelf.getId());
						}
					}
				}
			}
			if(row.getCell(3)==null|| StringUtils.isEmpty(row.getCell(3).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "出版社不能为空");
				objects.add(jsonObject);
			}
			if(row.getCell(4)==null|| StringUtils.isEmpty(row.getCell(4).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "作者不能为空");
				objects.add(jsonObject);
			}
			if(row.getCell(5)==null|| StringUtils.isEmpty(row.getCell(5).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "价格不能为空");
				objects.add(jsonObject);
			}
			if(row.getCell(6)==null|| StringUtils.isEmpty(row.getCell(6).toString())){
				jsonObject = new JSONObject(); // 存放错误提示
				jsonObject.put("index", i + 1);
				jsonObject.put("message", "借阅量不能为空，没有请填0");
				objects.add(jsonObject);
			}
			if(objects.size()==0){
				book.setCreatetime(DateUtil.getSimpleFormatedDateNow());
				book.setName(row.getCell(0).toString());
				book.setPublisher(row.getCell(3).toString());
				book.setAuthor(row.getCell(4).toString());
				book.setPrice(row.getCell(5).toString());
				book.setBorrow(Integer.parseInt(row.getCell(6).toString().replace(".0","")));
				book.setStock(Integer.parseInt(row.getCell(7).toString().replace(".0","")));
				bookList.add(book);
			}
		}
		if(objects.size()>0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return objects;
		}
		bookRepository.saveAll(bookList);
		return objects;
	}

	public void deleteAll(List<Book> dataset){
		bookRepository.deleteAll(dataset);
	}

}
