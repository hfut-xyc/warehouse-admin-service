package com.admin.mapper;

import com.admin.pojo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer insert(User user);

    Integer update(User user);

    Integer deleteById(String userId);

    User selectByName(String username);

    Integer selectCountByName(String keyword);

    List<User> selectListByName(
        @Param("page") Integer page,
        @Param("pageSize") Integer pageSize,
        @Param("keyword") String keyword
    );

}
