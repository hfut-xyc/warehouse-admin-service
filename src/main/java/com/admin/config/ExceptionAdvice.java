package com.admin.config;


import lombok.extern.slf4j.Slf4j;
import com.admin.pojo.vo.ResultVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResultVO handleException(HttpServletRequest request, Exception e) {
        log.error("{}: {}", request.getRequestURI(), e.getMessage());
        return ResultVO.error(e.getMessage());
    }
}