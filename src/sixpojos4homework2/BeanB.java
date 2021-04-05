package sixpojos4homework2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanB {
	
	@Value("${name1}")
	private String name;
	
	@Value("${value1}")
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

	public BeanB() {}
	
	@Override
	public String toString() {
		return "BeanB from HomeWork2";
	}
	
	public void init() throws Exception  {
        System.out.println("Initialization of BeanB");
    }
 
	public void close() throws Exception   {
        System.out.println("Destroying BeanB");
    }
	
	public void anotherInitMethod(){
	     System.out.println("Calling anotherInitMethod in BeanB...");
	}

}
