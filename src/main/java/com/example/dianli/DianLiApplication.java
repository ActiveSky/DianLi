package com.example.dianli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.example.dianli.Mapper")
@EnableSwagger2
public class DianLiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DianLiApplication.class, args);
	}

}
