package com.book.manager.controller;


import com.book.manager.mapper.UserMapper;
import com.book.manager.model.BookPage;
import com.book.manager.model.User;
import com.book.manager.repository.UserRepository;
import com.book.manager.service.MyUserDetailsService;
import com.book.manager.service.UserService;
import com.book.manager.utils.JwtTokenUtils;
import com.book.manager.utils.MyPasswordEncoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserMapper userMapper;
	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public JSONObject login(@RequestBody User loginUser, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		User user = new User();
		UserDetails loginUserAfter = userDetailsService.loadUserByUsername(loginUser.getUsername());
		if(loginUserAfter==null||!new MyPasswordEncoder().matches(loginUser.getPassword(), loginUserAfter.getPassword())){
			jsonObject.put("code", 200);
			jsonObject.put("message", "失败");
			return jsonObject;
		}
		user.setUsername(loginUserAfter.getUsername());
		String token = jwtTokenUtils.generateToken(user);
		jsonObject.put("code", 200);
		jsonObject.put("message", "成功");
		jsonObject.put("token", token);
		jsonObject.put("user", loginUserAfter);
		return jsonObject;
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public BookPage<User> find(@RequestBody User query) {
		query.setIsPage(true);
		BookPage result = new BookPage();
		List<User> userList=userMapper.findAll(query);
		result.setList(userList);
		result.setTotal(userMapper.count());
		int totalPage=userMapper.count().intValue()/query.getSizePerPage();
		if(userMapper.count().intValue()%query.getSizePerPage()>0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

	/**
	 * @function:添加新用户
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public User saveUser(@RequestBody User dataset) {
		if(dataset.getId()==null){
			dataset.setPassword(new MyPasswordEncoder().encode("111111"));
			return userService.saveOrUpdate(dataset);
		}else{
			User user = userRepository.findById(dataset.getId()).orElse(new User());
			user.setUsername(dataset.getUsername());
			user.setRole(dataset.getRole());
			return userService.saveOrUpdate(user);
		}
	}

	/**
	 * @function:修改联系方式
	 */
	@RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
	public User updatePhone(@RequestBody User dataset) {
		User user = userRepository.findById(dataset.getId()).orElse(new User());
		user.setPhone(dataset.getPhone());
		return userService.saveOrUpdate(user);
	}

	/**
	 * @function:修改密码
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public User updatePassword(@RequestBody User dataset) {
		User user = userRepository.findById(dataset.getId()).orElse(new User());
		user.setPassword(new MyPasswordEncoder().encode(dataset.getPassword()));
		return userService.saveOrUpdate(user);
	}

	/**
	 * @function:获取加密后的密码
	 */
	@RequestMapping(value = "/getPassword", method = RequestMethod.POST)
	public String getPassword(@RequestBody JSONObject dataset) {
		return new MyPasswordEncoder().encode(dataset.getString("oldpassword"));
	}

	/**
	 * @function:更改状态
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public User updateStatus(@RequestBody User dataset) {
		User user = userRepository.findById(dataset.getId()).get();
		user.setStatus(dataset.getStatus());
		return userService.saveOrUpdate(user);
	}


	@GetMapping({"detail/{id}"})
	public User findDetail(@PathVariable("id") String id) {
		User dataset = userRepository.findById(id).orElse(new User());
		return dataset;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public JSONObject deleteUser(@RequestBody User dataset) {
		JSONObject jo = new JSONObject();
		userService.delete(dataset);
		jo.put("success",true);
		return jo;
	}

	/**
	 * 检查登录名是否存在
	 */
	@RequestMapping(value="/checkUser",method=RequestMethod.POST)
	public JSONObject checkUser(@RequestBody User dataset) {
		JSONObject jo = new JSONObject();
		User user = userDetailsService.checkUserByUsername(dataset.getUsername());
		if(user!=null&&!user.getId().equals(dataset.getId())){
			jo.put("success",false);
		}else{
			jo.put("success",true);
		}
		return jo;
	}

	@RequestMapping(value = "/importUser", method = RequestMethod.POST)
	public JSONArray importUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		JSONArray infos=userService.importUser(file);
		return infos;
	}

}
