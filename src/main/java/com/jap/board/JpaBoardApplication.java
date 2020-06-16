package com.jap.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class JpaBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBoardApplication.class, args);
	}

	/**
	 * HiddenHttpMethodFilter는 요청 파라미터에 "_method"가 있을 경우 파라미터의 value을 요청 방식으로 사용하도록 스프링 MVC의 관련 정보를 설정하는 역활
	 */
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() { 
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		return hiddenHttpMethodFilter;
	}
}
