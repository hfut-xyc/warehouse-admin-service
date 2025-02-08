package com.admin.mapper;

import com.admin.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    Integer insert(Product product);

    Integer update(Product product);

    Integer deleteById(String productId);

    Product selectByPid(String productId);

    Product selectByName(String productName);

    Integer selectCountByName(String keyword);

    List<Product> selectListByName(
        @Param("page") Integer page,
        @Param("pageSize") Integer pageSize,
        @Param("keyword") String keyword
    );

}
