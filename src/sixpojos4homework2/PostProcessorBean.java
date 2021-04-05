package sixpojos4homework2;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class PostProcessorBean implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Calling PostProcessorean.postProcessBeforeInitialization");
        System.out.println(" Before Bean = " + bean + ", BeanName = " + beanName);
        
        if (bean instanceof BeanA) {
            ((BeanA) bean).setName("BeanA");
            ((BeanA) bean).setValue(333);
        }
        if (bean instanceof BeanE) {
            ((BeanE) bean).setName("BeanE");
            ((BeanE) bean).setValue(4444);
        }
        if (bean instanceof BeanF) {
            ((BeanF) bean).setName("BeanF");
            ((BeanF) bean).setValue(55555);
        }
        if (bean instanceof FactoryPostBean) {
            ((BeanF) bean).setName("FactoryPostBean");
            ((BeanF) bean).setValue(666666);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("inside BeanPostProcessor.postProcessBeforeInitialization");
        System.out.println(" After Bean = " + bean + ", BeanName = " + beanName);
        return bean;
    }
}

