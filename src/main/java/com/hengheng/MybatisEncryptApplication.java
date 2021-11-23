package com.hengheng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@MapperScan("com.hengheng.dao")
@SpringBootApplication
public class MybatisEncryptApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisEncryptApplication.class, args);
	}

}
