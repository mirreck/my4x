package net.my4x.services.dungeon;

import org.springframework.stereotype.Service;

import net.my4x.services.dungeon.digger.Digger;
import net.my4x.services.dungeon.model.Dungeon;
import net.my4x.services.dungeon.model.Level.TileType;

@Service
public class DungeonServiceImpl implements DungeonService {
   
   
   
   public static void main(String[] args) {
      Dungeon d = new Dungeon(-3, 0, -5, 5, -5, 5);
      d.getLevel(0).setValue(0, 0, TileType.FLOOR);
      System.out.println("D="+d);
   }

   public Dungeon generate() {
      Dungeon d = new Dungeon(-1, 0, -10, 10, -10, 10);
      Digger digger = new Digger(d);
      digger.work();
      return d;
   }
}
