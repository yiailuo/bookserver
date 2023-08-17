package com.book.manager.service;

import com.book.manager.model.User;
import com.book.manager.repository.UserRepository;
import com.book.manager.utils.DateUtil;
import com.book.manager.utils.MyPasswordEncoder;
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
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	public User saveOrUpdate(User zb) {
		zb.setCreatetime(DateUtil.getSimpleFormatedDateNow());
		return userRepository.save(zb);
	}

	public void delete(User dataset){
		userRepository.delete(dataset);
	}

	public JSONArray importUser(MultipartFile file){
		JSONArray objects = new JSONArray();
		JSONObject jsonObject = null;
		// 创建输入工作流
		InputStream is = null;
		try {
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
			List<User> userList=new ArrayList<>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				User user=new User();
				if(row.getCell(0)==null|| StringUtils.isEmpty(row.getCell(0).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "登录名不能为空");
					objects.add(jsonObject);
				}
				if(row.getCell(1)==null|| StringUtils.isEmpty(row.getCell(1).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "角色不能为空");
					objects.add(jsonObject);
				}
				if(row.getCell(2)==null|| StringUtils.isEmpty(row.getCell(2).toString())){
					jsonObject = new JSONObject(); // 存放错误提示
					jsonObject.put("index", i + 1);
					jsonObject.put("message", "密码不能为空");
					objects.add(jsonObject);
				}
				if(objects.size()==0){
					user.setUsername(row.getCell(0).toString());
					user.setRealname(row.getCell(1).toString());
					user.setRole(row.getCell(1).toString());
					user.setPassword(new MyPasswordEncoder().encode(row.getCell(2).toString()));
					user.setStatus("1");
					user.setCreatetime(DateUtil.getSimpleFormatedDateNow());
					userList.add(user);
				}
			}
			if(objects.size()>0){
				return objects;
			}
			userRepository.saveAll(userList);
		} catch (Exception e) {
			jsonObject = new JSONObject(); // 存放错误提示
			jsonObject.put("message", "导入失败，请检查是否存在重复登录名");
			objects.add(jsonObject);
			e.printStackTrace();
		}
		return objects;
	}

	public void deleteAll(List<User> dataset){
		userRepository.deleteAll(dataset);
	}

}
