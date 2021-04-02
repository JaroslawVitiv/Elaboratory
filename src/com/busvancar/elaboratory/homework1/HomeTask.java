package com.busvancar.elaboratory.homework1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import beansInterfaces.Bar;
import beansInterfaces.CollectionBean;

import otherbeans.Andorra;
import otherbeans.Bhutan;
import otherbeans.Columbia;



/**
 * Servlet implementation class HomeTask
 */


public class HomeTask {
	
	
	public static void main(String[] args) {
		ApplicationContext factory1 = new AnnotationConfigApplicationContext(Task1.class);
		
		for (String beanName : factory1.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
		
		ApplicationContext factory2 = new AnnotationConfigApplicationContext(Task2.class);
		
		for (String beanName : factory2.getBeanDefinitionNames()) {
				System.out.println(beanName);
        }
		
		
		ApplicationContext factory3 = new AnnotationConfigApplicationContext(Task3.class);
	      Andorra andorra = factory3.getBean(Andorra.class);
	      andorra.drinkCoffe();
	      Bhutan bhutan = factory3.getBean(Bhutan.class);
	      bhutan.drinkCoffe();
	      Columbia columbia = factory3.getBean(Columbia.class);
	      columbia.drinkCoffe();

	    ApplicationContext factory4 = new AnnotationConfigApplicationContext(Task4.class);
	      	CollectionBean collectionBean = factory4.getBean(CollectionBean.class);
        	collectionBean.printDrinks();
        ApplicationContext factory5 = new AnnotationConfigApplicationContext(Task4.class);
		   Bar newBean = factory5.getBean(Bar.class);
		   newBean.serve();

		ApplicationContext factory6 = new AnnotationConfigApplicationContext(Task1.class, Task2.class, Task3.class, Task4.class);
	      for (String beanName : factory6.getBeanDefinitionNames()) {
	          System.out.println(beanName);
	      }
	}
	
}
