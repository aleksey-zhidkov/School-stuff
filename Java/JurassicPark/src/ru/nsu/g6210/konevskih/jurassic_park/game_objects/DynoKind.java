package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 23:30:42
 * To change this template use File | Settings | File Templates.
 */
public class DynoKind {

    public static final DynoKind TRAV = new DynoKind(DynoType.TRAVOYADNOE, 10, 100, Color.BLUE, 1);
    public static final DynoKind HISHNIK = new DynoKind(DynoType.HISHNIK, 15, 100, Color.RED, 1);

    private DynoType dynoType;
    private Color color;
    private double minMass;
    private double maxMass;
    private int eye;

    private DynoKind(DynoType dynoType, double minMass, double maxMass, Color color, int eye) {
        this.dynoType = dynoType;
        this.minMass = minMass;
        this.maxMass = maxMass;
        this.color = color;
        this.eye = eye;
    }

    public DynoType getDynoType() {
        return dynoType;
    }

    public double getMinMass() {
        return minMass;
    }

    public double getMaxMass() {
        return maxMass;
    }

    public Color getColor() {
        return color;
    }

    public int getEye() {
        return eye;
    }
}
