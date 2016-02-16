package com.reid.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan("com.reid")
@ImportResource({"classpath:persistence-context.xml"}) // this is where the property place holders are configured
public class AppConfig {
	

}
