package ru.nsu.g6210.konevskih.jurassic_park.map;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.GameObject;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Dyno;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Direction;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Grass;

import java.util.List;
import java.awt.*;

/**
 * User: Lesha_marina
 * Date: 01.12.2008
 */
public class GameMapFactory {
    private static final int MAP_SIZE = 50;

    public static GameMap createMap(List<GameObject> gameObjects) {
        Location[][] locations = new Location[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations[0].length; j++) {
                locations[i][j] = new Location();
            }
        }

        for (GameObject go : gameObjects) {
            int x;
            int y;
            do {
                x = (int) (Math.random() * MAP_SIZE);
                y = (int) (Math.random() * MAP_SIZE);
            } while (!locations[y][x].addGameObject(go));
            go.setLocation(new Point(x, y));

        }

        return new GameMap(locations);
    }

    /*
    test
    Direction[] dir = Direction.values();
        int dirIdx = 0;
        for (GameObject go : gameObjects) {
            int x = 25;
            int y = 25;
            if (go instanceof Grass) {
                if (dir[dirIdx] == Direction.CENTER) {
                    dirIdx++;
                }
                x += dir[dirIdx].getX();
                y += dir[dirIdx++].getY();
            }
            System.out.println(go + " " + x + " " + y);
            go.setLocation(new Point(x, y));
            locations[y][x].addGameObject(go);
            }
     */

    /*
    test2
     Direction[] dir = Direction.values();
        int dirIdx = 0;
        for (GameObject go : gameObjects) {
            int x = 25;
            int y = 25;
            if (go instanceof Grass) {
                while (dir[dirIdx].getY() != 0 || dir[dirIdx].getX() == 0) {
                    dirIdx++;
                }
                x += dir[dirIdx].getX();
                y += dir[dirIdx++].getY();
            }
            System.out.println(go + " " + x + " " + y);
            go.setLocation(new Point(x, y));
            locations[y][x].addGameObject(go);
        }
     */

    /*
   work
   for (GameObject go : gameObjects) {
           int x;
           int y;
           do {
               x = (int) (Math.random() * MAP_SIZE);
               y = (int) (Math.random() * MAP_SIZE);
           } while (!locations[y][x].addGameObject(go));
           go.setLocation(new Point(x, y));

       }
    */
}
