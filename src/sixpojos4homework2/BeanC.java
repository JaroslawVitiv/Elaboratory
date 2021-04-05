package sixpojos4homework2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanC {
	
	
	@Value("${name2}")
	private String name;
	
	@Value("${value2}")
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

	public BeanC() {}
	
	@Override
	public String toString() {
		return "BeanC from HomeWork2";
	}
	
	public void init() {
        System.out.println("Initialization of BeanC");
    }

	public void close() {
        System.out.println("Destroying BeanC");
    }
}
