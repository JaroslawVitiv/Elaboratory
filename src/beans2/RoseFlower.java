package beans2;

import org.springframework.stereotype.Component;

@Component
public class RoseFlower {
		public String name;
	    public String owner;

	    public RoseFlower() {}
	    
	    public RoseFlower(String name, String owner) {
	        this.name = name;
	        this.owner = owner;
	    }

	    public String getOrigin() {
	        return this.name + " " + this.owner;
	    }
}
