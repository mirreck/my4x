package net.my4x.integration;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class IntegrationTest {

    @Test
    public void test() {
        System.setProperty("spring.profiles.active", "on");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/integrationTestContext.xml");
        
        FeatureBean bean = ctx.getBean("featureBean",  net.my4x.integration.FeatureBean.class);
        
        System.out.println("property="+bean.getA());
        System.out.println("property="+bean.getB());
        System.out.println("property="+bean.getC());
        
        FeatureBean beanA = ctx.getBean("featureBeanA",  net.my4x.integration.FeatureBean.class);
        FeatureBean beanB = ctx.getBean("featureBeanB",  net.my4x.integration.FeatureBean.class);
        FeatureBean beanC = ctx.getBean("featureBeanC",  net.my4x.integration.FeatureBean.class);
        
        System.out.println("beanA="+beanA);
        System.out.println("beanB="+beanB);
        System.out.println("beanC="+beanC);
    }

}
