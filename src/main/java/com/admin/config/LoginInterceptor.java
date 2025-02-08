package com.admin.config;

import com.admin.utils.Constants;
import com.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new AuthException("未登录");
        }
        Claims claims = JwtUtils.parseToken(token);

        // 超级管理员的权限：用户的增删改查、仓库的增删改
        Integer role = (Integer) claims.get("role");
        String path = request.getServletPath();
        if (path.startsWith("/user") || "/warehouse".equals(path)) {
            if (role == Constants.ROLE_USER) {
                throw new AuthException("访问权限不足");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
