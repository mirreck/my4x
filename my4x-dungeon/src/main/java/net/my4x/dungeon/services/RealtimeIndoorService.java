package net.my4x.services.dungeon;

import net.my4x.services.dungeon.model.Pos;
import net.my4x.services.dungeon.model.realtime.Action;
import net.my4x.services.dungeon.model.realtime.Status;

public interface RealtimeIndoorService {
   Status getStatus();
   void performAction(Action action, String performer, Pos target);
}
