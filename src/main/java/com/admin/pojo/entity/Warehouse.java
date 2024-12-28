package com.admin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {
	private Integer warehouseId;
	private String warehouseName;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
