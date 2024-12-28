package com.admin.service;

import com.admin.mapper.WarehouseMapper;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.vo.ProductVO;
import com.admin.pojo.vo.WarehouseVO;
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

    //根据仓库名分页查询仓库
    public Map<String, Object> selectListByName(SelectListDTO dto) {
        Integer total = warehouseMapper.selectCountByName(dto.getKeyword());
        List<WarehouseVO> warehouseList = warehouseMapper.selectListByName(
                (dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("warehouseList", warehouseList);
        return map;
    }

    // 添加仓库
    @Transactional
	public Integer insert(Warehouse warehouse) throws Exception {
        Warehouse temp = warehouseMapper.selectByName(warehouse.getWarehouseName());
		if (temp != null) {
			throw new Exception("仓库名称重复");
		}
        Integer res = warehouseMapper.insert(warehouse);
        if (res != 1) {
            throw new Exception("添加仓库失败");
        }
        return 1;
    }


	@Transactional
	public Integer update(Warehouse warehouse) throws Exception  {
		Integer res = warehouseMapper.update(warehouse);
        if (res != 1) {
			throw new Exception("修改仓库失败");
		}
        return 1;
    }

    @Transactional
    public Integer deleteById(Integer warehouseId) throws Exception  {
        List<ProductVO> productList = warehouseProductMapper.selectProductByWid(warehouseId);
        if (!productList.isEmpty()) {
            throw new Exception("仓库仍有库存，无法删除");
        }
        Integer res = warehouseMapper.deleteById(warehouseId);
        if (res != 1) {
            throw new Exception("删除仓库失败");
        }
        return res;
    }

}
