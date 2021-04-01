package otherbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Columbia {
    @Autowired
    private OtherBeanC otherBeanC;

    public void drinkCoffe() {
        otherBeanC.pouring();
    }
}