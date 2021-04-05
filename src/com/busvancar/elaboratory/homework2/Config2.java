package com.busvancar.elaboratory.homework2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import sixpojos4homework2.BeanA;
import sixpojos4homework2.BeanE;
import sixpojos4homework2.BeanF;

@Configuration
@PropertySource("classpath:beans.properties")

public class Config2 {
	@Bean
	BeanA getBeanA() {
		return new BeanA();
	}
	
	@Bean
	BeanE getBeanE() {
		return new BeanE();
	}
	
	@Lazy
	@Bean
	BeanF getBeanF() {
		return new BeanF();
	}
}
