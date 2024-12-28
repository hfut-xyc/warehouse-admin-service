package com.admin.mapper;

import com.admin.pojo.entity.Warehouse;
import com.admin.pojo.vo.WarehouseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseMapper {

	Integer insert(Warehouse warehouse);

	Integer update(Warehouse warehouse);

	Integer deleteById(Integer warehouseId);

	Warehouse selectByWid(Integer warehouseId);

	Warehouse selectByName(String warehouseName);

	Integer selectCountByName(String keyword);

	List<WarehouseVO> selectListByName(
			@Param("page") Integer page,
			@Param("pageSize") Integer pageSize,
			@Param("keyword") String keyword
	);

}
