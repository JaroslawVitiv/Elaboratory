package beans1;

import org.springframework.stereotype.Component;

@Component
public class BeanB  {

	 public String coffeeName;
	    public String countryName;

	    public BeanB() {}
	    
	    public BeanB(String coffeName, String countryName) {
	        this.coffeeName = coffeName;
	        this.countryName = countryName;
	    }

	    public String getOrigin() {
	        return this.coffeeName + " " + this.countryName;
	    }
}
