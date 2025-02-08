package com.admin.service;

import com.admin.config.BusinessException;
import com.admin.mapper.WarehouseMapper;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.entity.WarehouseProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;

    @Resource
    private WarehouseProductMapper warehouseProductMapper;

    // 添加仓库
    @Transactional
    public Warehouse insert(Warehouse warehouse) throws BusinessException {
        Warehouse temp = warehouseMapper.selectByName(warehouse.getWarehouseName());
        if (temp != null) {
            throw new BusinessException("仓库名称重复");
        }
        Integer res = warehouseMapper.insert(warehouse);
        if (res != 1) {
            throw new BusinessException("添加仓库失败");
        }
        return warehouse;
    }


    @Transactional
    public Warehouse update(Warehouse warehouse) throws BusinessException {
        Integer res = warehouseMapper.update(warehouse);
        if (res != 1) {
            throw new BusinessException("修改仓库失败");
        }
        return warehouse;
    }

    @Transactional
    public Integer deleteById(String warehouseId) throws BusinessException {
        List<WarehouseProduct> records = warehouseProductMapper.selectByWidPid(warehouseId, null);
        if (!records.isEmpty()) {
            throw new BusinessException("仓库仍有库存，无法删除");
        }
        Integer res = warehouseMapper.deleteById(warehouseId);
        if (res != 1) {
            throw new BusinessException("删除仓库失败");
        }
        return res;
    }

    /**
     * 根据仓库名分页查询仓库
     */
    public Map<String, Object> selectListByName(SelectListDTO dto) {
        Integer total = warehouseMapper.selectCountByName(dto.getKeyword());
        List<Warehouse> warehouseList = warehouseMapper.selectListByName(
                (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("warehouseList", warehouseList);
        return map;
    }

}
