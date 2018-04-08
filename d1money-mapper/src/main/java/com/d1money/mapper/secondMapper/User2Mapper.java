package com.d1money.mapper.secondMapper;

import com.yp.apientity.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
//@Component(value = "User2Mapper")
public interface User2Mapper {

    @Insert("insert into user2(name, password) values (#{name}, #{password})")
    void add(User user);

    @Select("select name,password from user2 where name = #{name}")
    User get(String name);
}
