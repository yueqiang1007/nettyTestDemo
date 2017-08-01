package com.rrkd;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
@MapperScan("com.rrkd.demo.Dao")
public class LogDemo extends SpringBootServletInitializer {
	static final Logger log = Logger.getLogger(LogDemo.class);

	public static void main(String[] args) {

		SpringApplication.run(LogDemo.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LogDemo.class);
	}

}
