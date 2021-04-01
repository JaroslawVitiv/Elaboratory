package beans3;



import org.springframework.stereotype.Component;

@Component
public class BeanD {

	 public String coffeeName;
	    public String countryName;

	    public BeanD() {}
	    
	    public BeanD(String coffeName, String countryName) {
	        this.coffeeName = coffeName;
	        this.countryName = countryName;
	    }

	    public String getOrigin() {
	        return this.coffeeName + " " + this.countryName;
	    }

   
}
