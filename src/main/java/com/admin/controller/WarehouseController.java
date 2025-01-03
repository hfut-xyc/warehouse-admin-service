package com.admin.controller;

import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.vo.ProductVO;
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

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body)
    {
        Map<String, Object> map = warehouseService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

    @PostMapping("/{warehouseId}/product")
    public ResultVO productList(@PathVariable Integer warehouseId) {
        List<ProductVO> productList = warehouseProductMapper.selectProductByWid(warehouseId);
        return ResultVO.ok("查询成功", productList);
    }

    @PostMapping("")
    public ResultVO insert(@RequestBody Warehouse warehouse) throws Exception {
        Integer res = warehouseService.insert(warehouse);
        return ResultVO.ok("添加成功", res);
    }

    @PutMapping("")
    public ResultVO update(@RequestBody Warehouse warehouse) throws Exception {
        Integer res = warehouseService.update(warehouse);
        return ResultVO.ok("修改成功", res);
    }

    @DeleteMapping("")
    public ResultVO delete(@RequestParam Integer id) throws Exception {
        Integer res = warehouseService.deleteById(id);
        return ResultVO.ok("删除成功", res);
    }

}
