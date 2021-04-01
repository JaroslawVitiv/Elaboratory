package beansInterfaces;


import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Primary
@Component
@Order(3)
public class Espresso implements Coffee {
    @Override
    public String toString() {
        return "Espresso";
    }
}