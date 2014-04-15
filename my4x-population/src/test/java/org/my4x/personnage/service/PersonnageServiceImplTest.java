package org.my4x.personnage.service;




import java.util.List;

import net.my4x.population.model.Personnage;
import net.my4x.population.service.PersonnageService;
import net.my4x.population.service.PersonnageServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonnageServiceImplTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(PersonnageServiceImplTest.class);
   
   private PersonnageService service;
   
   @Before
   public void init(){
      service = new PersonnageServiceImpl();
   }
   
   @Test
   public void test() {
     List<Personnage> generateFamilly = service.generateFamilly().getPersonnages();
     for (Personnage personnage : generateFamilly) {
        LOGGER.info(personnage.toString());
   }
     
     
   }


}
