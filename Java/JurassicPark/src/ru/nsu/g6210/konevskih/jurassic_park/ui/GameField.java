package ru.nsu.g6210.konevskih.jurassic_park.ui;

import ru.nsu.g6210.konevskih.jurassic_park.map.GameMap;
import ru.nsu.g6210.konevskih.jurassic_park.map.Location;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Dyno;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.*;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 22:15:33
 * To change this template use File | Settings | File Templates.
 */
public class GameField extends Canvas {

    private GameMap map;
    private int cellSize;

    public GameField(GameMap map) {
        this.map = map;
        cellSize = (Toolkit.getDefaultToolkit().getScreenSize().height - 20)/ map.getHeigth();
        setBackground(Color.WHITE);
    }

    public boolean isDoubleBuffered() {
        return true;
    }

    public void paint(Graphics g) {
        for (int i = 0; i < map.getHeigth(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Grass grass = null;
                Color dynoColor = null;
                Location loc = map.getLocation(j, i);
                for (GameObject go : loc.getGameObjects()) {
                    if (go instanceof Grass) {
                        grass = (Grass) go;
                    } else if (go instanceof Dyno) {
                        dynoColor = ((Dyno) go).getDynoKind().getColor();
                    }
                    if (grass != null && dynoColor != null) {
                        break;
                    }
                }
                if (grass != null) {
                    g.setColor(Color.GREEN);
                    int p = (int) Math.round(grass.getMass() * cellSize / Grass.MAX_VALUE);
                    int x = Math.round((cellSize - p) / 2) + 1;
                    g.fillOval(j * cellSize + x, i * cellSize + x, p, p);
                }
                if (dynoColor != null) {
                    g.setColor(dynoColor);
                    g.drawLine(j * cellSize, i * cellSize, j * cellSize + cellSize, i * cellSize + cellSize);
                    g.drawLine(j * cellSize + cellSize, i * cellSize, j * cellSize, i * cellSize + cellSize);
                }
            }
        }
    }

}
