package net.my4x.dungeon.services.digger;

public class DiggerOrder implements Comparable<DiggerOrder>{

   private DiggerAction action;
   public DiggerOrder(DiggerAction action, int occurence) {
      super();
      this.action = action;
      this.occurence = occurence;
   }
   public DiggerOrder(DiggerAction action) {
      super();
      this.action = action;
      this.occurence = action.getDefaultOccurence();
   }

   private int occurence = 0;
   
   public int compareTo(DiggerOrder another) {
      return another.occurence - this.occurence;
   }

   public DiggerAction getAction() {
      return action;
   }

   public int getOccurence() {
      return occurence;
   }

}
