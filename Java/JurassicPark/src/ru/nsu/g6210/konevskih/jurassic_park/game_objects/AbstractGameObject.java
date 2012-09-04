package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 02.12.2008
 * Time: 0:51:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractGameObject implements GameObject {

    protected Point location;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
