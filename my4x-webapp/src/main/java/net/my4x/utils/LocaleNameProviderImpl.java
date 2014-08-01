package net.my4x.utils;

import java.util.Locale;
import java.util.spi.LocaleNameProvider;

public class LocaleNameProviderImpl extends LocaleNameProvider{

    public LocaleNameProviderImpl(){
        super();
        System.err.print("Init LocaleNameProviderImpl");
    }

    @Override
    public String getDisplayLanguage(String languageCode, Locale locale) {
        return "Kurd";
    }

    @Override
    public String getDisplayCountry(String countryCode, Locale locale) {
        return "Iraq";
    }

    @Override
    public String getDisplayVariant(String variant, Locale locale) {
        return "Kurd";
    }

    @Override
    public Locale[] getAvailableLocales() {
        return new Locale[] {new Locale("ku"),new Locale("kj"), new Locale("kf")};
    }

}
