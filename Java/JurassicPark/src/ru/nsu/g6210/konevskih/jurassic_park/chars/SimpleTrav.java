package ru.nsu.g6210.konevskih.jurassic_park.chars;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.*;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 02.12.2008
 * Time: 0:16:30
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTrav extends AbstractDyno {

    public void makeTurn() throws Exception {
        if (engine.canEat(this)) {
            engine.eat(this);
            return;
        }
        List<GameObjectInfo> gameObjectInfos = engine.lookAround(this);
        GrassInfo maxGrass = null;
        //System.out.println(gameObjectInfos);
        for (GameObjectInfo go : gameObjectInfos) {
            if (go instanceof GrassInfo) {
          //      System.out.println(go + ": " + ((GrassInfo) go).getMass());
            }
            if (go instanceof GrassInfo &&
                    (maxGrass == null ||
                            ((GrassInfo) go).getMass() > maxGrass.getMass())) {
                maxGrass = (GrassInfo) go;
            }
        }
        if (maxGrass != null) {
            //System.out.println(maxGrass.getDirection());
        }
        //System.out.println("====================================");
        if (maxGrass != null && maxGrass.getDirection() != Direction.CENTER &&
                maxGrass.getMass() > 5) {
            //System.out.println("Move to havka");
            if (engine.canMove(this, maxGrass.getDirection())) {
                engine.move(this, maxGrass.getDirection());
            }
            return;
        }
            Direction randomDirection;
            do {
                randomDirection = Direction.getRandomDirection();
            } while (!engine.canMove(this, randomDirection));
            engine.move(this, randomDirection);
    }

    public Sex getSex() {
        return Math.random() > 0.5 ? Sex.FEMALE : Sex.MALE;
    }

    public DynoKind getDynoKind() {
        return DynoKind.TRAV;
    }
}
