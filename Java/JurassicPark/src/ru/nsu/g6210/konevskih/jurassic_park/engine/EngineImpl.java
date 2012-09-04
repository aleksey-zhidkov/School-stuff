package ru.nsu.g6210.konevskih.jurassic_park.engine;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.*;
import ru.nsu.g6210.konevskih.jurassic_park.map.Location;
import ru.nsu.g6210.konevskih.jurassic_park.map.GameMap;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:12:06
 * To change this template use File | Settings | File Templates.
 */
public class EngineImpl implements Engine {

    private List<Turner> turners = new ArrayList<Turner>();
    private List<EngineListener> listeners = new ArrayList<EngineListener>();
    private Map<AbstractDyno, Dyno> dynos = new HashMap<AbstractDyno, Dyno>();
    private GameMap map;

    public EngineImpl() {
    }

    public void run() {
        while (true) {
            for (Turner go : turners) {
                Dyno d = dynos.get(go);
                if (go instanceof AbstractDyno && d == null) {
                    continue;
                }
                try {
                    if (go instanceof AbstractDyno) {
                        d.decMassPercents(3);
                        if (d.getMass() <= d.getDynoKind().getMinMass()) {
                            dynos.remove(go);
                            map.getLocation(d.getLocation()).removeGameObject(d);
                            System.out.println("Death");
                            continue;
                        }
                    }
                    go.makeTurn();
                } catch (Error e) {
                    e.printStackTrace();
                    break;
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            for (EngineListener listener : listeners) {
                listener.turnMaked();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void addListener(EngineListener engineListener) {
        listeners.add(engineListener);
    }

    public DynoInfo getDynoInfo(AbstractDyno forDyno, AbstractDyno about) {
        return (DynoInfo) dynos.get(about).createGameObjectInfo(dynos.get(forDyno));
    }

    public Dyno registerDyno(AbstractDyno abstractDyno) {
        Dyno dyno = new Dyno();
        dyno.setDynoType(abstractDyno.getDynoKind());
        dyno.setSex(abstractDyno.getSex());
        dyno.setMass(abstractDyno.getDynoKind().getMinMass() + 5);
        turners.add(abstractDyno);
        dynos.put(abstractDyno, dyno);
        return dyno;
    }

    public void registerGrass(Grass grass) {
        turners.add(grass);
    }

    public boolean canEat(AbstractDyno abstractDyno) {
        Dyno dyno = dynos.get(abstractDyno);
        Location loc = map.getLocation(dyno.getLocation());
        for (GameObject go : loc.getGameObjects()) {
            if (dyno.getDynoKind().getDynoType() == DynoType.TRAVOYADNOE &&
                    go instanceof Grass && go.getMass() > 0) {
                return true;
            }
        }
        return false;
    }

    public void eat(AbstractDyno abstractDyno) {
        if (!canEat(abstractDyno)) {
            throw new IllegalStateException("You cannot eat here");
        }
        Dyno d = dynos.get(abstractDyno);
        Grass g = (Grass) map.getLocation(d.getLocation()).getGameObjects().get(0);
        double eatAmount = d.getMass() * 0.2;
        if (eatAmount > g.getMass()) {
            d.incMass(g.getMass());
            g.setMass(0.0D);
        } else {
            d.incMass(eatAmount);
            g.decMass(eatAmount);
        }
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public List<GameObjectInfo> lookAround(AbstractDyno dyno) {
        List<GameObjectInfo> res = new ArrayList<GameObjectInfo>();
        Dyno d = dynos.get(dyno);
        Point loc = d.getLocation();
        int eye = dyno.getDynoKind().getEye();
        int i = loc.y - eye;
        if (i < 0) {
            i = 0;
        }

        for (; i <= loc.y + eye && i < map.getHeigth(); i++) {
            int j = loc.x - eye;
            if (j < 0) {
                j = 0;
            }
            for (; j <= loc.x + eye && j < map.getWidth(); j++) {
                for (GameObject go : map.getLocation(j, i).getGameObjects()) {
                    res.add(go.createGameObjectInfo(dynos.get(dyno)));
                }
            }
        }
        return res;
    }

    public boolean canMove(AbstractDyno abstractDyno, Direction direction) {
        Dyno d = dynos.get(abstractDyno);
        int newX = d.getLocation().x + direction.getX();
        int newY = d.getLocation().y + direction.getY();
        return newX >= 0 && newY >= 0 && newX < map.getWidth() && newY < map.getHeigth() &&
                map.getLocation(newX, newY).offer(d);
    }

    public void move(AbstractDyno abstractDyno, Direction direction) {
        if (!canMove(abstractDyno, direction)) {
            return;
        }
        Dyno d = dynos.get(abstractDyno);
        map.getLocation(d.getLocation()).removeGameObject(d);
        d.move(direction);
        map.getLocation(d.getLocation()).addGameObject(d);
    }
}
