package net.my4x.dungeon.services;

import net.my4x.dungeon.model.Pos;
import net.my4x.dungeon.services.model.realtime.Action;
import net.my4x.dungeon.services.model.realtime.Status;

public interface RealtimeIndoorService {
   Status getStatus();
   void performAction(Action action, String performer, Pos target);
}
