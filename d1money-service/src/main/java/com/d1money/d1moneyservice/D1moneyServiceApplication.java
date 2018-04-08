package com.d1money.d1moneyservice;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(scanBasePackages = "com.d1money.d1moneyservice.service", exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {"com.d1money.mapper.firstMapper","com.d1money.mapper.secondMapper"})
@ComponentScan(basePackages = "com.d1money.**")
@EnableTransactionManagement
@EnableCaching
public class D1moneyServiceApplication implements CommandLineRunner, DisposableBean{

	private  final Logger log = LoggerFactory.getLogger(D1moneyServiceApplication.class);
	private final static CountDownLatch latch = new CountDownLatch(1);
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) throws  Exception{
		context = SpringApplication.run(D1moneyServiceApplication.class, args);
		latch.await();
	}

	@Override
	public void destroy() throws Exception {
		latch.countDown();
		context.close();
		log.info("服务提供者关闭------>>服务关闭");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("服务提供者启动完毕------>>启动完毕");
	}
}
