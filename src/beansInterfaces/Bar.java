package beansInterfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Bar {
    
    @Autowired
    @Qualifier("mocca")
    private Coffee drinkMocca;
    
    @Autowired
    @Qualifier("capuccino")
    private Coffee drinkCapuccino;

    private Coffee drink;
	private Coffee drinkIrish;
	
	@Autowired
    @Qualifier("aulait")
	private Coffee drinkAuLait;
    
    @Autowired
    @Qualifier("irish")
    public void setIrish(Coffee drinkIrish) {
        this.drinkIrish = drinkIrish;
    }
    
    
    @Autowired
    public Bar(Coffee drink) {
        this.drink = drink;
    }
    
    public void serve(){
        System.out.println(drink);
        System.out.println(drinkMocca);
        System.out.println(drinkCapuccino);
        System.out.println(drinkIrish);
        System.out.println(drinkAuLait);

    }
}
