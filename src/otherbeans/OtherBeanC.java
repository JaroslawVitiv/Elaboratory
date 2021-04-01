package otherbeans;

import org.springframework.stereotype.Component;

@Component("otherBeanC")
public class OtherBeanC {
    public void pouring() {
        System.out.println("I love coffee from BeanC");
    }
}