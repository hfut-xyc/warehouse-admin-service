package com.admin.controller;

import com.admin.config.JwtUtils;
import com.admin.pojo.dto.LoginDTO;
import com.admin.pojo.vo.ResultVO;
import com.admin.pojo.entity.User;
import com.admin.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {

    @Resource
    private UserService userService;

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
}
