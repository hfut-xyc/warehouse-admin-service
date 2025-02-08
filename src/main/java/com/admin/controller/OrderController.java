package com.admin.controller;

import com.admin.config.BusinessException;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Order;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.OrderService;
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

    @PostMapping("/update-stock")
    public ResultVO updateStockOrder(@RequestBody Order order) throws BusinessException {
        Order res = orderService.updateStock(order);
        return ResultVO.ok("下单成功", res);
    }

    @PostMapping("/insert-stock")
    public ResultVO insertStockOrder(@RequestBody Order order) throws BusinessException {
        Order res = orderService.insertStock(order);
        return ResultVO.ok("下单成功", res);
    }

    @DeleteMapping("/{orderId}")
    public ResultVO deleteById(@PathVariable String orderId) throws BusinessException {
        Integer res = orderService.deleteById(orderId);
        return ResultVO.ok("删除订单成功", res);
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        Map<String, Object> map = orderService.selectListByDate(body);
        return ResultVO.ok("查询成功", map);
    }
}
