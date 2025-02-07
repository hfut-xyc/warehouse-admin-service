package com.admin.controller;

import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Order;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/update-stock")
    public ResultVO updateStockOrder(@RequestBody Order order) throws Exception {
        return ResultVO.ok("下单成功", orderService.updateStock(order));
    }

    @PostMapping("/insert-stock")
    public ResultVO insertStockOrder(@RequestBody Order order) throws Exception {
        return ResultVO.ok("下单成功", orderService.updateStock(order));
    }

    @DeleteMapping("")
    public ResultVO deleteById(@PathVariable String orderId) throws Exception {
        return ResultVO.ok("删除订单成功", orderService.deleteById(orderId));
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        return ResultVO.ok("查询成功", orderService.selectListByDate(body));
    }
}
