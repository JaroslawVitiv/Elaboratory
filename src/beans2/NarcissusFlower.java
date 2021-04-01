package beans2;

import org.springframework.stereotype.Component;

@Component
public class NarcissusFlower {
	 	public String name;
	    public String owner;

	    public NarcissusFlower() {}
	    
	    public NarcissusFlower(String name, String owner) {
	        this.name = name;
	        this.owner = owner;
	    }

	    public String getOrigin() {
	        return this.name + " " + this.owner;
	    }
}
