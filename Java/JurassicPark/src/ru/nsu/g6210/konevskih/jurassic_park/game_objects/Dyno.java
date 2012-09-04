package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import ru.nsu.g6210.konevskih.jurassic_park.engine.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:35:46
 * To change this template use File | Settings | File Templates.
 */
public class Dyno extends AbstractGameObject implements GameObject {

    private DynoKind dynoKind;
    private Sex sex;
    private Double mass;

    public boolean canPlaceWith(GameObject gameObject) {
        return !(gameObject instanceof AbstractDyno);
    }

    public void setDynoType(DynoKind dynoKind) {
        this.dynoKind = dynoKind;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public DynoKind getDynoKind() {
        return dynoKind;
    }

    public Sex getSex() {
        return sex;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public GameObjectInfo createGameObjectInfo(Dyno forDyno) {
        return new DynoInfo(dynoKind, sex, Size.EQUAL, Direction.getDirection(forDyno.getLocation(), getLocation()));
    }

    public void incMass(Double mass) {
        this.mass += mass;
    }

    public void decMassPercents(int percents) {
        mass -= mass * percents / 100;
    }

    public void move(Direction direction) {
        location.x += direction.getX();
        location.y += direction.getY();
    }
}
