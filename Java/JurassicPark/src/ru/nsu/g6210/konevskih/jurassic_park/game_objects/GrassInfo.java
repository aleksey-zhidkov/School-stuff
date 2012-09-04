package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 04.12.2008
 * Time: 0:27:03
 * To change this template use File | Settings | File Templates.
 */
public class GrassInfo implements GameObjectInfo {

    private Direction direction;
    private Double mass;

    public GrassInfo(Direction direction, Double mass) {
        this.direction = direction;
        this.mass = mass;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getMass() {
        return mass;
    }
}
