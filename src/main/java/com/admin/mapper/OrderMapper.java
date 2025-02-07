package com.admin.mapper;

import org.apache.ibatis.annotations.*;
import com.admin.pojo.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    Integer insert(Order order);

    Integer deleteById(String orderId);

    Integer selectCountByDate(String date);

	List<Order> selectListByDate(
	        @Param("page") Integer page,
            @Param("pageSize") Integer pageSize,
            @Param("keyword") String keyword
    );
}
