package net.my4x.population.model;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class Personnage {
   private final String uuid;
   private final Gender gender;
   private final String firstName;
   private final String lastName;
   private final Date birthDate;
   // optional
   private Date deathDate = null;
   private Blason blason = null;
   private String surname; 
   
   private String coupleUuid;
   
   private String fatherUuid;
   private String motherUuid;
   
   private int generation = 0;
   
   public Personnage(Gender gender, String firstName, String lastName, Date birthDate) {
      if(gender == null){
         throw new IllegalArgumentException("Gender cannot be null");
      }
      this.uuid = UUID.randomUUID().toString();
      this.gender = gender;
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthDate = birthDate;
   }
   
   public void setFather(Personnage father){
      this.fatherUuid = father.uuid;
   }

   public void setMother(Personnage mother){
      this.motherUuid = mother.uuid;
   }
   
   
   public String getUuid() {
      return uuid;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public String getFatherUuid() {
      return fatherUuid;
   }

   public String getMotherUuid() {
      return motherUuid;
   }

   public Date getBirthDate() {
      return birthDate;
   }
   
   public String getStrBirthDate() {
      
      return DateFormatUtils.format(birthDate, "yyyy");
   }
   
   
   public Date getDeathDate() {
      return deathDate;
   }

   public String getStrDeathDate() {
      if(deathDate == null){
         return "";
      }
      return DateFormatUtils.format(deathDate, "yyyy");
   }
   
   public Gender getGender() {
      return gender;
   }
   
   public Blason getBlason() {
      return blason;
   }

   public void setBlason(Blason blason) {
      this.blason = blason;
   }

   public void setDeathDate(Date deathDate) {
      this.deathDate = deathDate;
   }

   public int getGeneration() {
      return generation;
   }

   public void setGeneration(int generation) {
      this.generation = generation;
   }

   @Override
   public String toString() {
      return "Personnage [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", generation="
            + generation + "]";
   }

   public String getCoupleUuid() {
      return coupleUuid;
   }

   public void setCoupleUuid(String coupleUuid) {
      this.coupleUuid = coupleUuid;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }
}
