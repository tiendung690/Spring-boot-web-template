package com.scc.webtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.dialect.springdata.SpringDataDialect;


@ComponentScan(
        basePackages = { "com.scc.webtemplate" }
)

@SpringBootApplication()
public class TemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}
	
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }	
	
}
