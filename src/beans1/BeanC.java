package beans1;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

	 public String coffeeName;
	    public String countryName;

	    public BeanC() {}
	    
	    public BeanC(String coffeName, String countryName) {
	        this.coffeeName = coffeName;
	        this.countryName = countryName;
	    }

	    public String getOrigin() {
	        return this.coffeeName + " " + this.countryName;
	    }
}