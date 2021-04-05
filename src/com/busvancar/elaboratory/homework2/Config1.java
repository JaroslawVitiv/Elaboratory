package com.busvancar.elaboratory.homework2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import sixpojos4homework2.BeanA;
import sixpojos4homework2.BeanB;
import sixpojos4homework2.BeanD;
import sixpojos4homework2.BeanE;
import sixpojos4homework2.BeanF;
import sixpojos4homework2.FactoryPostBean;
import sixpojos4homework2.PostProcessorBean;
import sixpojos4homework2.BeanC;

@Configuration
@PropertySource("classpath:beans.properties")
@Import(Config2.class)
public class Config1 {
	
	@Bean(initMethod = "init", destroyMethod = "close")
	BeanB getBeanB() {
		return new BeanB();
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
	BeanC getBeanC() {
		return new BeanC();
	}
	
	@Bean(initMethod = "init", destroyMethod = "close")
	BeanD getBeanD() {
		return new BeanD();
	}
	
	@Bean
    public static FactoryPostBean factoryPostBean() {
        return new FactoryPostBean();
    }

    @Bean
    public PostProcessorBean postProcessorBean() {
        return new PostProcessorBean();
    }
	
}