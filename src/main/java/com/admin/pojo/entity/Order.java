package com.admin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

	private Integer id;
	private String orderId;
	private String warehouseId;
	private String warehouseName;
	private String productId;
	private String productName;
	private String userId;
	private String username;
	private Integer count;
	private Integer stock;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
