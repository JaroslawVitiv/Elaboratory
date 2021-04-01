package beansInterfaces;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Qualifier("mocca")
@Component
@Order(2)
public class Mocca implements Coffee{

    @Override
    public String toString() {
        return "Mocca";
    }

}