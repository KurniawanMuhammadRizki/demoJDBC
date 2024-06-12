package com.kukilabs.demoJDBC;

import com.kukilabs.demoJDBC.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class DemoJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJdbcApplication.class, args);
	}

}
