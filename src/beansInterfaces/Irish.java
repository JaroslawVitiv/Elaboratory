package beansInterfaces;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Qualifier("irish")
@Component
@Order(4)
public class Irish implements Coffee {
    @Override
    public String toString() {
        return "Irish with Bayleys";
    }
}
