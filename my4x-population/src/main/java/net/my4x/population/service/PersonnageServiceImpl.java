package net.my4x.population.service;

import java.util.ArrayList;
import java.util.List;

import net.my4x.population.model.Blason;
import net.my4x.population.model.BlasonElement;
import net.my4x.population.model.FakerDwarf;
import net.my4x.population.model.Family;
import net.my4x.population.model.Gender;
import net.my4x.population.model.HeradicFigure;
import net.my4x.population.model.HeraldicColor;
import net.my4x.population.model.Personnage;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class PersonnageServiceImpl implements PersonnageService {

   private static final Logger LOGGER = LoggerFactory.getLogger(PersonnageServiceImpl.class);
   
   private FakerDwarf faker = new FakerDwarf();
   
   private int currentYear = 1900;
   
   private enum RecurseDirection{NONE,UP,DOWN};
   
   public Family generateFamilly() {
      ArrayList<Personnage> list = Lists.newArrayList();
      Personnage me = generatePersonnage(randomGender(),currentYear-25,currentYear-18,list,0, RecurseDirection.UP);
      Family family = new Family(list);
      Personnage f = family.getPersoMap().get(me.getFatherUuid());
      Personnage m = family.getPersoMap().get(me.getMotherUuid());
      for (Personnage personnage : list) {
         
         if(personnage.getUuid().equals(me.getUuid())){
            personnage.setSurname("Me !");
         } else if(personnage.getUuid().equals(me.getFatherUuid())){
            personnage.setSurname("Dad");
         } else if(personnage.getUuid().equals(me.getMotherUuid())){
            personnage.setSurname("Mom");
         } else if(personnage.getMotherUuid() != null && personnage.getMotherUuid().equals(me.getMotherUuid())){
            if(personnage.getGender() == Gender.M){
               personnage.setSurname("Brother");
            } else {
               personnage.setSurname("Sister");
            }
         } else if(personnage.getUuid().equals(f.getMotherUuid())
               || personnage.getUuid().equals(m.getMotherUuid())){
            personnage.setSurname("Gran'ma "+personnage.getFirstName());
         } else if(personnage.getUuid().equals(f.getFatherUuid())
               || personnage.getUuid().equals(m.getFatherUuid())){
            personnage.setSurname("Gran'pa "+personnage.getFirstName());
         } else if(personnage.getMotherUuid() != null && 
               (personnage.getMotherUuid().equals(f.getMotherUuid())|| personnage.getMotherUuid().equals(m.getMotherUuid()))){
            if(personnage.getGender() == Gender.M){
               personnage.setSurname("Uncle "+personnage.getFirstName());
            } else {
               personnage.setSurname("Aunt "+personnage.getFirstName());
            }
         }
         
      }
      return family;
   }
   

   private void generateParents(Personnage pers, List<Personnage> family){
      int birthYear = new DateTime(pers.getBirthDate()).getYear();
      //LOGGER.info("birthyear ="+birthYear);
      Personnage father = generatePersonnage(Gender.M,birthYear-40, birthYear-18,family,pers.getGeneration() +1, pers.getLastName(), RecurseDirection.UP);
      Personnage mother = generatePersonnage(Gender.F,birthYear-40, birthYear-18,family,pers.getGeneration() +1, RecurseDirection.UP);
      father.setCoupleUuid(mother.getUuid());
      mother.setCoupleUuid(father.getUuid());
      pers.setFather(father);
      pers.setMother(mother);
      generateChildren(father, mother, family);
   }
   private void generateChildren(Personnage father, Personnage mother, List<Personnage> family){
      int childnum =  faker.intInInterval(0, 3);
      int birthYear = new DateTime(mother.getBirthDate()).getYear();
      for (int i = 0; i <  childnum; i++) {
         
         Personnage child = generatePersonnage(randomGender(),birthYear+18, birthYear+40,family,mother.getGeneration() - 1, father.getLastName(), RecurseDirection.DOWN);
         child.setFather(father);
         child.setMother(mother);
         
      }
   }
   
   private Personnage generatePersonnage(Gender gender, int yearmin, int yearmax, List<Personnage> family, int generation, RecurseDirection dir){
      return generatePersonnage(gender, yearmin, yearmax, family, generation, StringUtils.capitalize(faker.lastName()), dir);
   }
   
   private Personnage generatePersonnage(Gender gender, int yearmin, int yearmax, List<Personnage> family, int generation, String lastName, RecurseDirection dir){
      Personnage p = new Personnage(gender,faker.firstName(toGender(gender)),lastName, faker.randomDate(yearmin, yearmax));
      int birthYear = new DateTime(p.getBirthDate()).getYear();
      int deathYear = birthYear + faker.intInInterval(50, 95);
      p.setGeneration(generation);
      p.setBlason(randomBlason());
      if(deathYear < currentYear) {
         p.setDeathDate(faker.randomDate(deathYear-1, deathYear));
      }
      if(dir == RecurseDirection.UP && generation < 2){
         generateParents(p, family);
      } else if(dir == RecurseDirection.DOWN && generation >=0){
         //generateChildren(father, mother, family);
      }
      family.add(p);
      return p;
   }

   private Gender randomGender(){
      return faker.randomElement(Gender.values());
   }
   private HeraldicColor randomHeraldicColor(){
      return faker.randomElement(HeraldicColor.values());
   }
   private HeradicFigure randomHeradicFigure(){
      return faker.randomElement(HeradicFigure.primaryFigures());
   }
   private Blason randomBlason(){
      Blason b = new Blason();
      HeraldicColor maincolor = randomHeraldicColor();
      
      b.add(new BlasonElement(HeradicFigure.BASE,maincolor));
      b.add(new BlasonElement(randomHeradicFigure(),faker.randomElement(maincolor.matchingColors())));
      return b;
   }
   
   private com.github.javafaker.domain.Gender toGender(Gender gender) {
      return com.github.javafaker.domain.Gender.valueOf(gender.name());
   }
   

}
