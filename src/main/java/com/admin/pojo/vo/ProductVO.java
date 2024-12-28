package com.admin.pojo.vo;

import com.admin.pojo.entity.WarehouseProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
	private Integer productId;
	private String productName;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	private List<WarehouseProduct> warehouseProductList;

}
