package net.my4x.utils;

import com.github.javafaker.Faker;

public class RandomUtils {
   public static void main(String[] args) {
      Faker faker = new Faker();
     
      String name = faker.name(); // Miss Samanta Schmidt
      String firstName = faker.firstName(); // Emory
      String lastName = faker.lastName(); // Barton
      for (int i = 0; i < 100; i++) {
         System.out.println("name="+faker.streetName());
      }
      
   }
}
