package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 23:49:14
 * To change this template use File | Settings | File Templates.
 */
public class DynoInfo implements GameObjectInfo {

    private Sex sex;
    private Size size;
    private DynoKind dynoKind;
    private Direction direction;

    public DynoInfo(DynoKind dynoKind, Sex sex, Size size, Direction direction) {
        this.dynoKind = dynoKind;
        this.sex = sex;
        this.size = size;
        this.direction = direction;
    }

    public Sex getSex() {
        return sex;
    }

    public Size getSize() {
        return size;
    }

    public DynoKind getDynoKind() {
        return dynoKind;
    }

    public Direction getDirection() {
        return direction;
    }
}
