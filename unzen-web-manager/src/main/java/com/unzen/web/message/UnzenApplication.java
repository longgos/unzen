package com.unzen.web.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.unzen.common.dao")
/**
 * 由于项目有多子项目，而默认情况下只能扫描与控制器在同一个包下以及其子包下的@Component注解 。
 * 以及能将指定注解的类自动注册为Bean的@Service@Controller和@ Repository，
 * 所以，在指定的application类上加上这么一行注解，手动指定application类要扫描哪些包下的注解
 */
@ComponentScan(basePackages = { "com.unzen.*" })
public class UnzenApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnzenApplication.class, args);
	}

}