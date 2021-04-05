package sixpojos4homework2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

@Component
public class FactoryPostBean implements BeanFactoryPostProcessor {
	
	private String name;
	private int value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public FactoryPostBean() {}
	
	@Override
	public String toString() {
		return "FactoryPostBean implements BeanFactoryPostProcessor from HomeWork2";
	}

	 @Override
	    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	        GenericBeanDefinition bd = new GenericBeanDefinition();
	        bd.setBeanClass(BeanB.class);
	        bd.setInitMethodName("anotherInitMethod");
	        ((DefaultListableBeanFactory) beanFactory)
	                .registerBeanDefinition("getBeanB", bd);
	    
		System.out.println("Initializing FactoryPostBean with BeanFactoryPostProcessor");
	}

}
