package net.my4x.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.spi.LocaleNameProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    
    
    public static void main(String[] args) {
        
        ServiceLoader<LocaleNameProvider> load = ServiceLoader.load(LocaleNameProvider.class);
        
        
        Locale[] availableLocales = Locale.getAvailableLocales();
        System.out.println("Locale COUNT="+availableLocales.length);
        ArrayList<Locale> list = Lists.newArrayList(availableLocales);
        Collections.sort(list, new Comparator<Locale>() {
            @Override
            public int compare(Locale o1, Locale o2) {
                return o1.getLanguage().compareTo(o2.getLanguage());
            }
        });


        for (Locale locale : list) {
            //if(locale.getLanguage().startsWith("ku")){
                
                LOGGER.info("Locale "+locale.getLanguage());

            //}
            
        }
    }
}
