package com.admin.controller;

import com.admin.config.BusinessException;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.dto.UserLoginDTO;
import com.admin.pojo.entity.User;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.UserService;
import com.admin.utils.JwtUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("")
    public ResultVO insert(@RequestBody User user) throws BusinessException {
        User res = userService.insert(user);
        return ResultVO.ok("添加成功", res);
    }

    @PutMapping("")
    public ResultVO update(@RequestBody User user) throws BusinessException {
        User res = userService.update(user);
        return ResultVO.ok("修改成功", res);
    }

    @DeleteMapping("/{userId}")
    public ResultVO delete(@PathVariable String userId) throws BusinessException {
        Integer res = userService.deleteById(userId);
        return ResultVO.ok("删除成功", res);
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody @Validated UserLoginDTO body) throws BusinessException {
        User user = userService.login(body);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());
        String token = JwtUtils.createToken(claims);

        return ResultVO.ok("登录成功", token);
    }

    @PostMapping("/logout")
    public ResultVO logout() {
        //TODO
        return ResultVO.ok("注销成功");
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        Map<String, Object> map = userService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

}
