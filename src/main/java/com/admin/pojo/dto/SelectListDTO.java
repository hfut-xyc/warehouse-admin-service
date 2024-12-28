package com.admin.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectListDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword;
    private String date;
}
