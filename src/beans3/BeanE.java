package beans3;


import org.springframework.stereotype.Component;


@Component
public class BeanE{

	 public String coffeeName;
	    public String countryName;

	    public BeanE() {}
	    
	    public BeanE(String coffeName, String countryName) {
	        this.coffeeName = coffeName;
	        this.countryName = countryName;
	    }

	    public String getOrigin() {
	        return this.coffeeName + " " + this.countryName;
	    }

   
}
