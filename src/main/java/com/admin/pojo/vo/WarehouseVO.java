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
public class WarehouseVO implements Serializable {
	private Integer warehouseId;
	private String warehouseName;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	private List<WarehouseProduct> warehouseProductList;

}
