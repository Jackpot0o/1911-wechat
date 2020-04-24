package com.qf.weixingateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinGatewayApplicationTests {

	@Test
	public void contextLoads() {

		String fileName = "M00/00/00/wKjByF3fazWATim1AAE_4jKbLyc925.jpg";
		String minHead = fileName.replaceAll(".jpg","_100x100.jpg");
		System.out.println(minHead);
	}

}
