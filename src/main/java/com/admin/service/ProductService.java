package com.admin.service;

import com.admin.config.BusinessException;
import com.admin.mapper.ProductMapper;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Product;
import com.admin.pojo.entity.WarehouseProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    @Transactional
    public Product insert(Product product) throws BusinessException {
        Product temp = productMapper.selectByName(product.getProductName());
        if (temp != null) {
            throw new BusinessException("产品名已存在");
        }
        Integer res = productMapper.insert(product);
        if (res != 1) {
            throw new BusinessException("添加产品失败");
        }
        return product;
    }

    @Transactional
    public Product update(Product product) throws BusinessException {
        Integer res = productMapper.update(product);
        if (res != 1) {
            throw new BusinessException("修改产品失败");
        }
        return product;
    }


    @Transactional
    public Integer deleteById(String productId) throws BusinessException {
        List<WarehouseProduct> records = warehouseProductMapper.selectByWidPid(null, productId);
        if (!records.isEmpty()) {
            throw new BusinessException("产品仍有库存，无法删除");
        }
        Integer res = productMapper.deleteById(productId);
        if (res != 1) {
            throw new BusinessException("删除产品失败");
        }
        return res;
    }

    /**
     * 根据产品名分页查询产品
     */
    public Map<String, Object> selectListByName(SelectListDTO dto) {
        Integer total = productMapper.selectCountByName(dto.getKeyword());
        List<Product> productList = productMapper.selectListByName(
                (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("productList", productList);
        return map;
    }
}
