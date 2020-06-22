package com.jap.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class JpaBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBoardApplication.class, args);
	}

	/**
	 * HiddenHttpMethodFilter는 요청 파라미터에 "_method"가 있을 경우 파라미터의 value을 요청 방식으로
	 * 사용하도록 스프링 MVC의 관련 정보를 설정하는 역활
	 */
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		return hiddenHttpMethodFilter;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("JPA_BOARD")
		                                              .apiInfo(apiInfo())
		                                              .select()
		                                              .paths(PathSelectors.ant("/api/**"))
		                                              .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("JPA_BOARD API")
		                           .description("JPA_BOARD API")
		                           .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
		                           .contact("Niklas Heidloff")
		                           .license("Apache License Version 2.0")
		                           .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
		                           .version("2.0")
		                           .build();
	}
}
