package org.server.controller;

import org.server.entity.User;
import org.server.exception.RegisterException;
import org.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/user/add")
	public int addUser(@RequestBody User user) {
		try {
			userService.addUser(user);
			return 1;
		} catch (RegisterException e1) {
			logger.error(e1.getMessage());
		} catch (Exception e2) {
			logger.error(e2.getMessage());
		}
		return 0;
	}

	@PostMapping("/user/{id}/enabled")
	public int updateUserEnabled(@RequestParam("enabled") boolean enabled, @PathVariable int id) {
		return userService.updateUserEnabled(enabled, id);
	}

	@DeleteMapping("/user/{id}/delete")
	public int deleteUserById(@PathVariable int id) {
		return userService.deleteUserById(id);
	}

	@GetMapping("/users")
	public Map<String, Object> getUserList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "keyword", required = false) String keyword)
	{
		int total = userService.getUserCount(keyword);
		List<User> list = userService.getUserList(keyword);
		int start = (page - 1) * pageSize;
		int end = Math.min(start + pageSize, list.size());
		// 此处返回的total和userList的长度并不一定相等，userList的长度仅表示一页的数据量
		Map<String, Object> map = new HashMap<>();
		map.put("userList", list.subList(start, end));
		map.put("total",total);
		return map;
	}
}