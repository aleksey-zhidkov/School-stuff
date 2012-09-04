package ru.nsu.g6210.konevskih.jurassic_park.game_objects;

import ru.nsu.g6210.konevskih.jurassic_park.engine.Engine;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 23:47:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDyno implements Turner {

    protected Engine engine;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public abstract Sex getSex();
    public abstract DynoKind getDynoKind();

}
