package ru.nsu.g6210.konevskih.jurassic_park.engine;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.*;
import ru.nsu.g6210.konevskih.jurassic_park.chars.SimpleTrav;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:03:59
 * To change this template use File | Settings | File Templates.
 */
public interface Engine {

    DynoInfo getDynoInfo(AbstractDyno forDyno, AbstractDyno about);

    boolean canEat(AbstractDyno simpleTrav);

    void eat(AbstractDyno simpleTrav);

    List<GameObjectInfo> lookAround(AbstractDyno dyno);

    boolean canMove(AbstractDyno abstractDyno, Direction direction);

    void move(AbstractDyno abstractDyno, Direction direction);
}
