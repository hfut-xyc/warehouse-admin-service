package com.admin.controller;

import com.admin.pojo.vo.ResultVO;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.User;
import com.admin.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body)
    {
        Map<String, Object> map = userService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

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
    public ResultVO delete(@RequestParam Integer id) throws Exception {
        Integer res = userService.deleteById(id);
        return ResultVO.ok("删除成功", res);
    }
}
