package net.my4x.services.dungeon.model.realtime;

import net.my4x.services.dungeon.model.Pos;

public class Event {
   private Action action;
   private Pos srcPosition;
   private Pos dstPosition;
   private String performerUuid;
   private String targetUuid;
   private long timestamp;
   
   
   private Event(Action action, Pos srcPosition, String performerUuid, long timestamp) {
      super();
      this.action = action;
      this.srcPosition = srcPosition;
      this.performerUuid = performerUuid;
      this.timestamp = timestamp;
   }
   
   private Event(Action action, Pos srcPosition, Pos dstPosition, String performerUuid, String targetUuid, long timestamp) {
      super();
      this.action = action;
      this.srcPosition = srcPosition;
      this.dstPosition = dstPosition;
      this.performerUuid = performerUuid;
      this.targetUuid = targetUuid;
      this.timestamp = timestamp;
   }
   
   



   public Action getAction() {
      return action;
   }
   public Pos getSrcPosition() {
      return srcPosition;
   }
   public Pos getDstPosition() {
      return dstPosition;
   }
   public String getPerformerUuid() {
      return performerUuid;
   }
   public String getTargetUuid() {
      return targetUuid;
   }
   public long getTimestamp() {
      return timestamp;
   }
}
