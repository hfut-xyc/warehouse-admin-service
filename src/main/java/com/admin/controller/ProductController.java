package com.admin.controller;

import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Product;
import com.admin.pojo.entity.WarehouseProduct;
import com.admin.pojo.vo.ResultVO;
import com.admin.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    @PostMapping("")
    public ResultVO insert(@RequestBody Product product) throws Exception {
        Integer res = productService.insert(product);
        return ResultVO.ok("添加成功", res);
    }

    @PutMapping("")
    public ResultVO update(@RequestBody Product product) throws Exception {
        Integer res = productService.update(product);
        return ResultVO.ok("修改成功", res);
    }

    @DeleteMapping("")
    public ResultVO delete(@RequestParam String productId) throws Exception {
        Integer res = productService.deleteById(productId);
        return ResultVO.ok("删除成功", res);
    }

    @PostMapping("/list")
    public ResultVO list(@RequestBody SelectListDTO body) {
        Map<String, Object> map = productService.selectListByName(body);
        return ResultVO.ok("查询成功", map);
    }

    @GetMapping("/{productId}")
    public ResultVO warehouseList(@PathVariable String productId) {
        List<WarehouseProduct> records = warehouseProductMapper.selectByWidPid(null, productId);
        return ResultVO.ok("查询成功", records);
    }
}
