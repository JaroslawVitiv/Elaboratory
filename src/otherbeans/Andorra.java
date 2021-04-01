package otherbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Andorra {
    private OtherBeanA otherBeanA;
    @Autowired
    public Andorra(OtherBeanA otherBeanA) {
        this.otherBeanA = otherBeanA;
    }
    public void drinkCoffe() {
        otherBeanA.pouring();
    }
}