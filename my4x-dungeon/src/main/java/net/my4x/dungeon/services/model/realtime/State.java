package net.my4x.dungeon.services.model.realtime;

import net.my4x.dungeon.model.Pos;

public class State {
   private String elementUuid;
   private Pos position;
   
   
   private State(String elementUuid, Pos position) {
      super();
      this.elementUuid = elementUuid;
      this.position = position;
   }
   public String getElementUuid() {
      return elementUuid;
   }
   public Pos getPosition() {
      return position;
   }
}
