package beansInterfaces;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Qualifier("aulait")
@Component
@Order(5)
public class AuLait implements Coffee {
    @Override
    public String toString() {
        return "Caffe au lait...";
    }
}
