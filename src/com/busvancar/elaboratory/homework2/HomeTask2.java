package com.busvancar.elaboratory.homework2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sixpojos4homework2.BeanA;
import sixpojos4homework2.BeanB;
import sixpojos4homework2.BeanC;
import sixpojos4homework2.BeanD;
import sixpojos4homework2.BeanE;
import sixpojos4homework2.FactoryPostBean;
import sixpojos4homework2.BeanF;


public class HomeTask2 {
	
	private static ApplicationContext ctxt;

	public static void main(String[] args)  {
		
		ctxt = new AnnotationConfigApplicationContext(Config1.class);
		
		BeanB bb = ctxt.getBean(BeanB.class);
		BeanC bc = ctxt.getBean(BeanC.class);
		BeanD bd = ctxt.getBean(BeanD.class);
		
		BeanA ba = ctxt.getBean(BeanA.class);
		BeanE be = ctxt.getBean(BeanE.class);
		BeanF bf = ctxt.getBean(BeanF.class);
		
		
		FactoryPostBean fpb = ctxt.getBean(FactoryPostBean.class);
		System.out.println("FactoryPostBean name: "+fpb.getName()+" --> FactoryPostBean value: "+fpb.getValue()); 

		System.out.println("Name: "+bb.getName()+" --> Value: "+bb.getValue()); 
		System.out.println("Name: "+bc.getName()+" --> Value: "+bc.getValue());  
		System.out.println("Name: "+bd.getName()+" --> Value: "+bd.getValue());  
		
		System.out.println("Name: "+ba.getName()+" --> Value: "+ba.getValue()); 
		System.out.println("Name: "+be.getName()+" --> Value: "+be.getValue());  
		System.out.println("Name: "+bf.getName()+" --> Value: "+bf.getValue());  

		System.out.println(" ----------- ");    
		for (String beanName : ctxt.getBeanDefinitionNames()) {
	       System.out.println(beanName);
	    }
		
	}
}
