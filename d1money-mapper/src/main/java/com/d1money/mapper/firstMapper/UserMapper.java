package com.d1money.mapper.firstMapper;

import com.yp.apientity.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
//@Component(value = "UserMapper")/**解决idea无法注入mapper的问题*/
public interface UserMapper {

    @Insert("insert into user(name, password) values (#{name}, #{password})")
    void add(User user);

    @Select("select name,password from user where name = #{name}")
    User get(String name);
}
