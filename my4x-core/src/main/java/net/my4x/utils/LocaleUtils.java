package net.my4x.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class LocaleUtils {

    
    public static void main(String[] args) {
        
        SecurityManager appsm = System.getSecurityManager();
        System.out.println("appsm="+appsm);
        try {
            Method method = Locale.class.getDeclaredMethod("getInstance", String.class, String.class, String.class);
            System.out.println("method="+method);
            method.setAccessible(true);
            method.invoke(null, "ku","ku","");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            
        }
        
        
        Locale[] availableLocales = Locale.getAvailableLocales();
        System.out.println("Locale COUNT="+availableLocales.length);
        for (Locale locale : availableLocales) {
            //if(locale.getLanguage().startsWith("ku")){
                System.out.println("Locale "+locale.getLanguage());
            //}
            
        }
        
        
        
    }
}
