package sixpojos4homework2;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;

import org.springframework.stereotype.Component;


@Component
public class BeanA implements InitializingBean, DisposableBean{
	
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

	public BeanA() {}
	
	@Override
	public String toString() {
		return "BeanA from HomeWork2";
	}
	

	@Override
	public void destroy() throws Exception {
        System.out.println("Destroying BeanA");		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
        System.out.println("Initialization of BeanA");
	}
}
