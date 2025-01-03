package com.admin.controller;

import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Order;
import com.admin.service.OrderService;
import com.admin.pojo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body)
    {
        Map<String, Object> map = orderService.selectListByDate(body);
        return ResultVO.ok("查询成功", map);
    }

    @PostMapping("")
    public ResultVO insert(@RequestBody Order order) throws Exception {
        Integer res = orderService.insert(order);
        return ResultVO.ok("添加成功", res);
    }


    @DeleteMapping("")
    public ResultVO deleteById(@RequestParam Integer id) throws Exception {
        Integer res = orderService.deleteById(id);
        return ResultVO.ok("删除成功", res);
    }
}
