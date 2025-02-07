package com.admin.controller;

import com.admin.pojo.dto.LoginDTO;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.User;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.UserService;
import com.admin.utils.JwtUtils;
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
    public ResultVO insert(@RequestBody User user) throws Exception {
        Integer res = userService.insert(user);
        return ResultVO.ok("添加成功", res);
    }

    @PutMapping("")
    public ResultVO update(@RequestBody User user) throws Exception {
        Integer res = userService.update(user);
        return ResultVO.ok("修改成功", res);
    }

    @DeleteMapping("")
    public ResultVO delete(@RequestParam String userId) throws Exception {
        Integer res = userService.deleteById(userId);
        return ResultVO.ok("删除成功", res);
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginDTO body) throws Exception {
        User user = userService.login(body);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("role", user.getRole());
        String token = JwtUtils.createToken(claims);

        return ResultVO.ok("登录成功", token);
    }

    @PostMapping("/logout")
    public ResultVO logout() throws Exception {
        //TODO
        return ResultVO.ok("注销成功");
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        Map<String, Object> map = userService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

}
