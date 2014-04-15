package net.my4x.population.service;

import java.util.ArrayList;
import java.util.List;

import net.my4x.population.model.Family;
import net.my4x.population.model.Gender;
import net.my4x.population.model.Personnage;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.javafaker.fr.FakerFr;
import com.google.common.collect.Lists;

@Service
public class PersonnageServiceImpl implements PersonnageService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PersonnageServiceImpl.class);
   
   private FakerFr faker = new FakerFr();
   
   private int currentYear = 1900;
   
   public Family generateFamilly() {
      ArrayList<Personnage> list = Lists.newArrayList();
      generatePersonnage(Gender.M,currentYear-25,currentYear-18,list,0);
      Family family = new Family();
      family.setPersonnages(list);
      return family;
   }
   

   private void generateParents(Personnage pers, List<Personnage> family){
      int birthYear = new DateTime(pers.getBirthDate()).getYear();
      //LOGGER.info("birthyear ="+birthYear);
      Personnage father = generatePersonnage(Gender.M,birthYear-40, birthYear-18,family,pers.getGeneration() +1, pers.getLastName());
      Personnage mother = generatePersonnage(Gender.F,birthYear-40, birthYear-18,family,pers.getGeneration() +1);
      father.setCoupleUuid(mother.getUuid());
      mother.setCoupleUuid(father.getUuid());
      pers.setFather(father);
      pers.setMother(mother);
   }
   private Personnage generatePersonnage(Gender gender, int yearmin, int yearmax, List<Personnage> family, int generation){
      return generatePersonnage(gender, yearmin, yearmax, family, generation, faker.lastName());
   }
   
   private Personnage generatePersonnage(Gender gender, int yearmin, int yearmax, List<Personnage> family, int generation, String lastName){
      Personnage p = new Personnage(gender,faker.firstName(toGender(gender)),lastName, faker.randomDate(yearmin, yearmax));
      int birthYear = new DateTime(p.getBirthDate()).getYear();
      int deathYear = birthYear + faker.intInInterval(50, 95);
      p.setGeneration(generation);
      if(deathYear < currentYear) {
         p.setDeathDate(faker.randomDate(deathYear-1, deathYear));
      } else {
         generateParents(p, family);
      }
      family.add(p);
      return p;
   }

   private com.github.javafaker.domain.Gender toGender(Gender gender) {
      return com.github.javafaker.domain.Gender.valueOf(gender.name());
   }

}
