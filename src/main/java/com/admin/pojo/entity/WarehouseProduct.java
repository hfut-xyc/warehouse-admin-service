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
	private Integer warehouseId;
	private String warehouseName;
	private Integer productId;
	private String productName;
	private Integer stock;

	private Integer createUserId;
	private String createUsername;

	private Integer updateUserId;
	private String updateUserName;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
