package com.admin.service;

import com.admin.pojo.dto.LoginDTO;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.User;
import com.admin.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	// 用户登录，根据用户名查询用户
	public User login(LoginDTO dto) throws Exception {
		String username = dto.getUsername();
		String password = dto.getPassword();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			throw new Exception("用户名和密码不能为空");
		}

		User user = userMapper.selectByName(username);
		if (user == null || !password.equals(user.getPassword())) {
			throw new Exception("登录失败");
		}
		return user;
	}

	// 根据用户名分页查询管理员用户
	public Map<String, Object> selectListByName(SelectListDTO dto) {
		Integer total = userMapper.selectCountByName(dto.getKeyword());
		List<User> userList = userMapper.selectListByName(
				(dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("userList", userList);
		return map;
	}

	@Transactional
	public Integer insert(User user) throws Exception {
		User temp = userMapper.selectByName(user.getUsername());
		if (temp != null) {
			throw new Exception("用户名已存在");
		}
		Integer res = userMapper.insert(user);
		if (res != 1) {
			throw new Exception("添加用户失败");
		}
		return res;
	}

	@Transactional
	public Integer update(User user) throws Exception {
		Integer res = userMapper.update(user);
		if (res != 1) {
			throw new Exception("修改用户失败");
		}
		return res;
	}

	@Transactional
	public Integer deleteById(Integer userId) throws Exception {
		Integer res = userMapper.deleteById(userId);
		if (res != 1) {
			throw new Exception("删除用户失败");
		}
		return res;
	}
}
