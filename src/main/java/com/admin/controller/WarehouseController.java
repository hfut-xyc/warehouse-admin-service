package com.admin.controller;

import com.admin.entity.Warehouse;
import com.admin.service.WarehouseService;
import com.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    @GetMapping("/list")
    public Result listWarehouse(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword)
    {
        Map<String, Object> map = warehouseService.selectListByName(keyword);
        return Result.ok("查询成功", map);
    }

    @PostMapping("")
    public Result insertWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        Integer res = warehouseService.insert(warehouse);
        return Result.ok("添加成功", res);
    }

    @PutMapping("")
    public Result updateWarehouse(@RequestBody Warehouse warehouse) throws Exception {
        Integer res = warehouseService.update(warehouse);
        return Result.ok("修改成功", res);
    }

    @DeleteMapping("")
    public Result deleteWarehouse(@RequestParam Integer id) throws Exception {
        Integer res = warehouseService.deleteById(id);
        return Result.ok("删除成功", res);
    }

}
