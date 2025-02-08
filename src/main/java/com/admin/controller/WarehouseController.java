package com.admin.controller;

import com.admin.config.BusinessException;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.entity.WarehouseProduct;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    @PostMapping("")
    public ResultVO insert(@RequestBody Warehouse warehouse) throws BusinessException {
        Warehouse res = warehouseService.insert(warehouse);
        return ResultVO.ok("添加成功", res);
    }

    @PutMapping("")
    public ResultVO update(@RequestBody Warehouse warehouse) throws BusinessException {
        Warehouse res = warehouseService.update(warehouse);
        return ResultVO.ok("修改成功", res);
    }

    @DeleteMapping("/{warehouseId}")
    public ResultVO delete(@PathVariable String warehouseId) throws BusinessException {
        Integer res = warehouseService.deleteById(warehouseId);
        return ResultVO.ok("删除成功", res);
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        Map<String, Object> map = warehouseService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

    /**
     * 查询仓库详情
     */
    @GetMapping("/{warehouseId}")
    public ResultVO productList(@PathVariable String warehouseId) {
        List<WarehouseProduct> records = warehouseProductMapper.selectByWidPid(warehouseId, null);
        return ResultVO.ok("查询成功", records);
    }
}
