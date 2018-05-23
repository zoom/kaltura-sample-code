/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */
package us.zoom.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("us.zoom.cms.mapper")
public class CmsconnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsconnectorApplication.class, args);
	}
}
