package net.my4x.dungeon.services;

import java.util.List;

import lombok.Builder;
import net.my4x.dungeon.model.Direction;
import net.my4x.dungeon.model.Position;

@Builder(builderMethodName = "hiddenBuilder")
public class Options {
    // mandatory
    Position start;
    Direction dir;
    public static Options.OptionsBuilder builder(Position start, Direction dir) {
        return hiddenBuilder().dir(dir).start(start).strategy(RoomService.DigStrategy.CAVE);
    }
    int widthX;
    int heightY;
    List<Position> exits;
    RoomService.DigStrategy strategy = RoomService.DigStrategy.CAVE;
}