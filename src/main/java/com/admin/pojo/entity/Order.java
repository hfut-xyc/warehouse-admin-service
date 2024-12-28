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

	private Integer orderId;
	private Integer warehouseId;
	private String warehouseName;
	private Integer productId;
	private String productName;
	private Integer count;

	private Integer createUserId;
	private String createUsername;
	private LocalDateTime createTime;

}
