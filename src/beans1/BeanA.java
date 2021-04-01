package beans1;

import org.springframework.stereotype.Component;

@Component
public class BeanA  {

    public String coffeeName;
    public String countryName;

    public BeanA() {}
    
    public BeanA(String coffeName, String countryName) {
        this.coffeeName = coffeName;
        this.countryName = countryName;
    }

    public String getOrigin() {
        return this.coffeeName + " " + this.countryName;
    }

   
}
