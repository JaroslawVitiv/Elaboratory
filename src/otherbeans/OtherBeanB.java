package otherbeans;

import org.springframework.stereotype.Component;

@Component("otherBeanB")

public class OtherBeanB {
    public void pouring() {
        System.out.println("I love coffee from BeanB");
    }
}