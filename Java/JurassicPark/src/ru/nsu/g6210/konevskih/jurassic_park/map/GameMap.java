package ru.nsu.g6210.konevskih.jurassic_park.map;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:08:04
 * To change this template use File | Settings | File Templates.
 */
public class GameMap {

    private Location[][] locations;

    public GameMap(Location[][] locations) {
        this.locations = locations;
    }

    public int getHeigth() {
        return locations.length;
    }

    public int getWidth() {
        return locations[0].length;
    }

    public Location getLocation(int x, int y) {
        return locations[y][x];
    }

    public Location getLocation(Point pos) {
        return getLocation(pos.x, pos.y);
    }

}
