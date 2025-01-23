package com.admin.mapper;

import com.admin.pojo.entity.WarehouseProduct;
import com.admin.pojo.vo.ProductVO;
import com.admin.pojo.vo.WarehouseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WarehouseProductMapper {

    // 添加一条库存记录
    Integer insert(WarehouseProduct warehouseProduct);

    // 更新原有库存记录
    Integer updateStock(WarehouseProduct warehouseProduct);

    // 查询库存记录
    WarehouseProduct selectByWidPid(Integer wid, Integer pid);

    // 查询产品pid所在的所有仓库
    List<WarehouseVO> selectWarehouseByPid(Integer pid);

    // 查询仓库wid包含的所有产品
    List<ProductVO> selectProductByWid(Integer wid);

}
