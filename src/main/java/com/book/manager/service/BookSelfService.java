package com.book.manager.service;

import com.book.manager.model.BookSelf;
import com.book.manager.repository.BookSelfRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookSelfService {

	@Autowired
	BookSelfRepository bookSelfRepository;

	@Transactional
	public BookSelf saveOrUpdate(BookSelf zb) {
		zb.setCreatetime(DateUtil.getSimpleFormatedDateNow());
		return bookSelfRepository.save(zb);
	}

	public void delete(BookSelf dataset){
		bookSelfRepository.delete(dataset);
	}

	public JSONArray importBookSelf(MultipartFile file){
		JSONArray objects = new JSONArray();
		JSONObject jsonObject = null;
		// 创建输入工作流
		InputStream is = null;
		try {
			is = file.getInputStream();
			Workbook wb = null;
			// 根据文件名判断文件是2003版本还是2007版本
			try {
				wb = new XSSFWorkbook(is);
			} catch (Exception ex) {
				wb = new HSSFWorkbook(is);
			}
			Sheet sheet=wb.getSheetAt(0);
			List<BookSelf> bookSelfList=new ArrayList<>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				BookSelf bookSelf=new BookSelf();
				if(row.getCell(0)==null|| StringUtils.isEmpty(row.getCell(0).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "书架号不能为空");
					objects.add(jsonObject);
				}
				if(row.getCell(1)==null|| StringUtils.isEmpty(row.getCell(1).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "剩余容量请填大于0的整数");
					objects.add(jsonObject);
				}
				if(row.getCell(2)==null|| StringUtils.isEmpty(row.getCell(2).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "已用容量不能为空，没有请填0");
					objects.add(jsonObject);
				}
				if(objects.size()==0){
					bookSelf.setCreatetime(DateUtil.getSimpleFormatedDateNow());
					bookSelf.setName(row.getCell(0).toString());
					bookSelf.setCapacity(Integer.parseInt(row.getCell(1).toString()));
					bookSelf.setUses(Integer.parseInt(row.getCell(2).toString()));
					bookSelfList.add(bookSelf);
				}
			}
			if(objects.size()>0){
				return objects;
			}
			bookSelfRepository.saveAll(bookSelfList);
		} catch (Exception e) {
			jsonObject = new JSONObject(); // 存放错误提示
			jsonObject.put("message", "导入失败，请检查是否存在重复书架号");
			objects.add(jsonObject);
			e.printStackTrace();
		}
		return objects;
	}

}
