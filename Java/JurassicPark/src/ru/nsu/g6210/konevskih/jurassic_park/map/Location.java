package ru.nsu.g6210.konevskih.jurassic_park.map;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.GameObject;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Dyno;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 21:48:44
 * To change this template use File | Settings | File Templates.
 */
public class Location {

    private List<GameObject> gameObjects = new ArrayList<GameObject>();

    public boolean addGameObject(GameObject gameObject) {
        for (GameObject go : gameObjects) {
            if (!go.canPlaceWith(gameObject)) {
                return false;
            }
        }
        gameObjects.add(gameObject);
        return true;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void removeGameObject(Dyno d) {
        gameObjects.remove(d);
    }

    public boolean offer(GameObject gameObject) {
        for (GameObject go : gameObjects) {
            if (!go.canPlaceWith(gameObject)) {
                return false;
            }
        }
        return true;
    }
}
