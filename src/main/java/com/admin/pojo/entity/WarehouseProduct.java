package com.admin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProduct implements Serializable {
	private Integer id;
	private String warehouseId;
	private String warehouseName;
	private String productId;
	private String productName;
	private Integer stock;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
