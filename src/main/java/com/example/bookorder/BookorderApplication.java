package com.example.bookorder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableSwagger2
@ControllerAdvice
public class BookorderApplication {

	@Value("${version.name}")
	private String versionName;

	public static void main(String[] args) {
		SpringApplication.run(BookorderApplication.class, args);
	}

	@Bean
	public Docket api() {
		Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));
		ApiInfo DEFAULT_API_INFO = new ApiInfo("Book Order", "Book Order", versionName, "", "Uğur Türkyılmaz", "", "");

		return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(
				Collections.singletonList(new ParameterBuilder()
						.name("Authorization")
						.description("Default token : Basic YWRtaW46cGFzc3dvcmQ=")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build())).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES).consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}

}
