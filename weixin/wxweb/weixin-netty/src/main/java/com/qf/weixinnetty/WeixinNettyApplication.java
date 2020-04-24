package com.qf.weixinnetty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.qf",exclude = DataSourceAutoConfiguration.class)
public class WeixinNettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinNettyApplication.class, args);
	}

}
