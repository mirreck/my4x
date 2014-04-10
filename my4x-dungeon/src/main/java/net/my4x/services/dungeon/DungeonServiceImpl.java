package net.my4x.services.dungeon;

import net.my4x.services.dungeon.digger.Digger;
import net.my4x.services.dungeon.model.Dungeon;
import net.my4x.services.dungeon.model.Pos;
import net.my4x.services.dungeon.model.TileType;

import org.springframework.stereotype.Service;

@Service
public class DungeonServiceImpl implements DungeonService {
   
   
   
   public static void main(String[] args) {
      Dungeon d = new Dungeon(-1, 0, -10, 10, -5, 5);
      d.getLevel(0).setValue(new Pos(0, 0), TileType.FLOOR);
      System.out.println("D="+d);
   }

   public Dungeon generate() {
      Dungeon d = new Dungeon(-2, 0, -10, 10, -10, 10);
      Digger digger = new Digger(d);
      digger.work();
      return d;
   }
}
