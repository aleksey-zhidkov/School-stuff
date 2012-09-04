package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 04.12.2008
 * Time: 0:17:02
 * To change this template use File | Settings | File Templates.
 */
public enum Direction {

    CENTER(0, 0),
    NORTH(0, 1),
    NORTH_EAST(1, 1),
    EAST(1, 0),
    SOUTH_EAST(1, -1),
    SOUTH(0, -1),
    SOUTH_WEST(-1, -1),
    WEST(-1, 0),
    NORTH_WEST(-1, 1);

    private static final Direction[][] directions = {{NORTH_WEST, NORTH, NORTH_EAST},
            {WEST, CENTER, EAST},
            {SOUTH_WEST, SOUTH, SOUTH_EAST}};

    private int x;
    private int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Direction getDirection(Point from, Point to) {
        int dx = to.x - from.x;
        int dy = from.y - to.y;
        if (dx != 0) {
            dx /= Math.abs(dx);
        }
        if (dy != 0) {
            dy /= Math.abs(dy);
        }
        return directions[dy + 1][dx + 1];
    }


    // todo for test pourposes, remove later
    public static void main(String[] args) {
        Point from = new Point(0,0);
        System.out.println(getDirection(from, new Point(0, 0)));
        System.out.println(getDirection(from, new Point(-1, 1)));
        System.out.println(getDirection(from, new Point(0, 1)));
        System.out.println(getDirection(from, new Point(1, 1)));
        System.out.println(getDirection(from, new Point(1, 0)));
        System.out.println(getDirection(from, new Point(1, -1)));
        System.out.println(getDirection(from, new Point(0, -1)));
        System.out.println(getDirection(from, new Point(-1, -1)));
        System.out.println(getDirection(from, new Point(-1, 0)));
    }

    public static Direction getRandomDirection() {
        int i;
        int j;
        do {
            i = (int) (Math.round(Math.random() * 2));
            j = (int) (Math.round(Math.random() * 2));
        } while (i == 1 && j == 1);
        return directions[i][j];
    }


    public String toString() {
        return super.toString() + ": (" + x + ", " + y + ")"; 
    }

}

