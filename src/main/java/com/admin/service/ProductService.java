package com.admin.service;

import com.admin.mapper.ProductMapper;
import com.admin.mapper.WarehouseProductMapper;
import com.admin.pojo.dto.SelectListDTO;
import com.admin.pojo.entity.Product;
import com.admin.pojo.vo.ProductVO;
import com.admin.pojo.vo.WarehouseVO;
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

	// 根据产品名分页查询产品以及所在的仓库
	public Map<String, Object> selectListByName(SelectListDTO dto) {
		Integer total = productMapper.selectCountByName(dto.getKeyword());
		List<ProductVO> productList = productMapper.selectListByName(
				(dto.getPage() - 1) * dto.getPageSize(), dto.getPageSize(), dto.getKeyword());

		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("productList", productList);
		return map;
	}

	@Transactional
	public Integer insert(Product product) throws Exception {
		Product temp = productMapper.selectByName(product.getProductName());
		if (temp != null) {
			throw new Exception("产品名已存在");
		}
		Integer res = productMapper.insert(product);
		if (res != 1) {
			throw new Exception("添加产品失败");
		}
		return 1;
	}

	@Transactional
	public Integer update(Product product) throws Exception  {
		Integer res = productMapper.update(product);
		if (res != 1) {
			throw new Exception("修改产品失败");
		}
		return 1;
	}


	@Transactional
	public Integer deleteById(Integer productId) throws Exception  {
		List<WarehouseVO> warehouseList = warehouseProductMapper.selectWarehouseByPid(productId);
		if (!warehouseList.isEmpty()) {
			throw new Exception("产品仍有库存，无法删除");
		}
		Integer res = productMapper.deleteById(productId);
		if (res != 1) {
			throw new Exception("删除产品失败");
		}
		return res;
	}
}
