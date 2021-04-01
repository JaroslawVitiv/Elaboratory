package beans3;


import org.springframework.stereotype.Component;


@Component
public class BeanF {

	 public String coffeeName;
	    public String countryName;

	    public BeanF() {}
	    
	    public BeanF(String coffeName, String countryName) {
	        this.coffeeName = coffeName;
	        this.countryName = countryName;
	    }

	    public String getOrigin() {
	        return this.coffeeName + " " + this.countryName;
	    }

   
}
