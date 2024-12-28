package com.admin.mapper;

import com.admin.pojo.entity.Product;
import com.admin.pojo.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

	Integer insert(Product product);

	Integer update(Product product);

	Integer deleteById(Integer productId);

	Product selectByPid(Integer productId);

	Product selectByName(String productName);

	Integer selectCountByName(String keyword);

	List<ProductVO> selectListByName(
			@Param("page") Integer page,
			@Param("pageSize") Integer pageSize,
			@Param("keyword") String keyword
	);

}
