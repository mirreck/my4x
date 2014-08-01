package net.my4x.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordGeneratorTest.class);
    
    @Test
    public void test() {
        WordGenerator wg = new WordGenerator();
        for (int i = 0; i < 20; i++) {
            LOGGER.debug("generated: {}", wg.generate());
            
        }
    }

}
