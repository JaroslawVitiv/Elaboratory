package com.busvancar.elaboratory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.FilterType;

import beans1.BeanA;
import beans1.BeanB;
import beans3.BeanE;


@Configuration
@ComponentScan(basePackages = "beans2, beans3", excludeFilters =@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BeanE.class) )


public class Task2 { }