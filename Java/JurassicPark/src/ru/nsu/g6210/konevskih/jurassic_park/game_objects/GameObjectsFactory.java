package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import ru.nsu.g6210.konevskih.jurassic_park.chars.SimpleTrav;
import ru.nsu.g6210.konevskih.jurassic_park.engine.Engine;
import ru.nsu.g6210.konevskih.jurassic_park.engine.EngineImpl;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:49:30
 * To change this template use File | Settings | File Templates.
 */
public class GameObjectsFactory {

    private static final int GRASS_COUNT = 600;
    private static final int TRAV_COUNT = 100;

    public static List<GameObject> createGameObjects(EngineImpl engine) {
        List<GameObject> res = new ArrayList<GameObject>(GRASS_COUNT + TRAV_COUNT);
        for (int i = 0; i < GRASS_COUNT; i++) {
            Grass grass = new Grass();
            engine.registerGrass(grass);
            res.add(grass);
        }
        for (int i = 0; i < TRAV_COUNT; i++) {
            SimpleTrav dyno = new SimpleTrav();
            dyno.setEngine(engine);
            res.add(engine.registerDyno(dyno));
        }
        return res;
    }

}
