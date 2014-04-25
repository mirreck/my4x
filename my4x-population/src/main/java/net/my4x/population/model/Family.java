package net.my4x.population.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Family {
   private List<Personnage> personnages;
   private Map<String,Personnage> persoMap;
   
   public Family(List<Personnage> personnages) {
      this.personnages = personnages;
      persoMap = new HashMap<String, Personnage>();
      for (Personnage personnage : personnages) {
         persoMap.put(personnage.getUuid(), personnage);
      }
   }
   
   
   public List<Personnage> getPersonnages() {
      return personnages;
   }


   public Map<String, Personnage> getPersoMap() {
      return persoMap;
   }

}
