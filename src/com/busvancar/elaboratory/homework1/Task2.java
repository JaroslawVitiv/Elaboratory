package com.busvancar.elaboratory.homework1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import beans3.BeanE;


@Configuration
@ComponentScan(basePackages = "beans2, beans3", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BeanE.class) )
public class Task2 { }