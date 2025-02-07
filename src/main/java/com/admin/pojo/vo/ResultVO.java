package com.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {

    private Integer code;
    private String message;
    private Object data;

    public static ResultVO ok(String message, Object data) {
        return new ResultVO(1, message, data);
    }

    public static ResultVO ok(String message) {
        return new ResultVO(1, message, null);
    }

    public static ResultVO error(String message) {
        return new ResultVO(-1, message, null);
    }

}
