package com.chan.rider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ChanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChanApplication.class, args);
	}

}
