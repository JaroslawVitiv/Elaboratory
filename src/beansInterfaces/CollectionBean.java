package beansInterfaces;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CollectionBean {
    @Autowired
    private List<Coffee> drink;
    public void printDrinks() {
        System.out.println("Drink:"+ drink);
    }
}