package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import ru.nsu.g6210.konevskih.jurassic_park.engine.Engine;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:01:13
 * To change this template use File | Settings | File Templates.
 */
public interface GameObject {

    boolean canPlaceWith(GameObject gameObject);
    void setLocation(Point p);
    Double getMass();
    GameObjectInfo createGameObjectInfo(Dyno forDyno);

}
