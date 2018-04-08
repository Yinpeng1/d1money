package com.d1money.d1moneyservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d1money.cache.service.RedisClient;
import com.d1money.mapper.firstMapper.UserMapper;
import com.d1money.mapper.secondMapper.User2Mapper;
import com.yp.apientity.Entity.User;
import com.yp.apiservice.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol="dubbo-hessian")
public class HelloServiceImpl implements HelloService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User2Mapper user2Mapper;

    @Autowired
    private RedisClient<User> redisClient;

    @Transactional
    public void add(User user){
        userMapper.add(user);
    }

    @Override
    public String sayHello() {
        return "Hello World";
    }

//    @Transactional  /** value为事物源的name 比如value = "test2TransactionManager"*/
    @Override
    public void add() {
        User user = new User();
        user.setName("qwerty");
        user.setPassword("12345567");
        redisClient.setKeyObj(user.getName(), user, 100);
        userMapper.add(user);
        user2Mapper.add(user);
    }

    @Override
    public User get(String s) {
        return null;
    }
}
