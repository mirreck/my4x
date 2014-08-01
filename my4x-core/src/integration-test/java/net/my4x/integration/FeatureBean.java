package net.my4x.integration;
import org.springframework.beans.factory.annotation.Value;


public class FeatureBean {
    @Value( "${feature.a}" )
    private String a;
    
    @Value( "${feature.b}" )
    private String b;
    
    @Value( "${feature.c}" )
    private String c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
    
    
}
