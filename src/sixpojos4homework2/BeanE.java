package sixpojos4homework2;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanE {
	
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

	public BeanE() {}
	
	@Override
	public String toString() {
		return "BeanE from HomeWork2";
	}
	
	@PostConstruct
	public void init() throws Exception {
        System.out.println("Initialization of BeanE");
	}
	
	@PreDestroy
	public void destroy() throws Exception {
        System.out.println("Destroying BeanE");		
	}

	

}
