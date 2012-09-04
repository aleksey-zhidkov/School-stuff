package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:18:59
 * To change this template use File | Settings | File Templates.
 */
public class Grass extends AbstractGameObject implements GameObject, Turner {

    private Double mass = (Math.random() * MAX_VALUE);
    public static final Double MAX_VALUE = 20D;

    public boolean canPlaceWith(GameObject gameObject) {
        return !(gameObject instanceof Grass);
    }

    public void makeTurn() {
        mass = mass + (mass * 0.01);
        if (mass > MAX_VALUE) {
            mass = MAX_VALUE;
        } else if (mass == 0 && Math.random() < 0.2) {
            mass = 1.0D;
        }
    }

    public Double getMass() {
        return mass;
    }

    public GameObjectInfo createGameObjectInfo(Dyno forDyno) {
        return new GrassInfo(Direction.getDirection(forDyno.getLocation(), getLocation()), mass);
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public void decMass(Double mass) {
        this.mass -= mass;
    }
}
