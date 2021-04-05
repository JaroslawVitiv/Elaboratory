package sixpojos4homework2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanD {
	
	
	@Value("${name3}")
	private String name;
	
	@Value("${value3}")
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

	public BeanD() {}
	
	@Override
	public String toString() {
		return "BeanD from HomeWork2";
	}
	
	public void init() {
        System.out.println("Initialization of BeanD");
    }

	public void close() {
        System.out.println("Destroying BeanD");
    }

}
