package com.busvancar.elaboratory.homework1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;


import beans1.BeanA;
import beans1.BeanB;


@Configuration
@ComponentScan(basePackages = "beans1")


public class Task1 {}