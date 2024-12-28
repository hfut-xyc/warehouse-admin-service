package com.admin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
	private Integer productId;
	private String productName;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;

}
