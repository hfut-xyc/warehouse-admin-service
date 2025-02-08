package com.admin.service;

import com.admin.config.BusinessException;
import com.admin.mapper.UserMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.dto.UserLoginDTO;
import com.admin.pojo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional
    public User insert(User user) throws BusinessException {
        User temp = userMapper.selectByName(user.getUsername());
        if (temp != null) {
            throw new BusinessException("用户名已存在");
        }
        Integer res = userMapper.insert(user);
        if (res != 1) {
            throw new BusinessException("添加用户失败");
        }
        return user;
    }

    @Transactional
    public User update(User user) throws BusinessException {
        Integer res = userMapper.update(user);
        if (res != 1) {
            throw new BusinessException("修改用户失败");
        }
        return user;
    }

    @Transactional
    public Integer deleteById(String userId) throws BusinessException {
        Integer res = userMapper.deleteById(userId);
        if (res != 1) {
            throw new BusinessException("删除用户失败");
        }
        return res;
    }

    /**
     * 用户登录，根据用户名查询用户
     */
    public User login(UserLoginDTO dto) throws BusinessException {
        String username = dto.getUsername();
        String password = dto.getPassword();
        User user = userMapper.selectByName(username);
        if (user == null || !password.equals(user.getPassword())) {
            throw new BusinessException("登录失败");
        }
        return user;
    }

    /**
     * 根据用户名分页查询管理员用户
     */
    public Map<String, Object> selectListByName(SelectListDTO dto) {
        Integer total = userMapper.selectCountByName(dto.getKeyword());
        List<User> userList = userMapper.selectListByName(
                (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("userList", userList);
        return map;
    }

}
