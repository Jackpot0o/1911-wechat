package com.qf.weixinfriend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@EnableEurekaClient
@EnableFeignClients("com.qf.service.api")
@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan(basePackages = "com.qf.mapper")
public class WeixinFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinFriendApplication.class, args);
	}

}
