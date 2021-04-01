package beansInterfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Bar {
    @Autowired
    private Coffee drink;
    public void serve()
    {
        System.out.println(drink);
        System.out.println(drinkMocca);
        System.out.println(drinkCapuccino);
    }
    @Autowired
    @Qualifier("mocca")
    private Coffee drinkMocca;
    @Autowired
    @Qualifier("capuccino")
    private Coffee drinkCapuccino;
}
