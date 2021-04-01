package otherbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bhutan {
    private OtherBeanB otherBeanB;

    @Autowired
    public void setOtherBeanB(OtherBeanB otherBeanB) {
        this.otherBeanB = otherBeanB;
    }

    public Bhutan() {
    }

    public void drinkCoffe() {
        otherBeanB.pouring();
    }
}