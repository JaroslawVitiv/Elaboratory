package beansInterfaces;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Qualifier("dog")
@Component
@Order(1)
public class Capuccino implements Coffee {
    @Override
    public String toString() {
        return "Capuccino";
    }
}
