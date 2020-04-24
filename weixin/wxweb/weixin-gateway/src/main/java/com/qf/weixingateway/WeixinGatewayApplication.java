package com.qf.weixingateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WeixinGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinGatewayApplication.class, args);
	}

}
