package ru.nsu.g6210.konevskih.jurassic_park;

import ru.nsu.g6210.konevskih.jurassic_park.game_objects.GameObject;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.GameObjectsFactory;
import ru.nsu.g6210.konevskih.jurassic_park.game_objects.Turner;
import ru.nsu.g6210.konevskih.jurassic_park.map.GameMap;
import ru.nsu.g6210.konevskih.jurassic_park.map.GameMapFactory;
import ru.nsu.g6210.konevskih.jurassic_park.ui.GameField;
import ru.nsu.g6210.konevskih.jurassic_park.engine.Engine;
import ru.nsu.g6210.konevskih.jurassic_park.engine.EngineImpl;
import ru.nsu.g6210.konevskih.jurassic_park.engine.EngineListener;

import javax.swing.*;
import java.util.List;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lesha_marina
 * Date: 01.12.2008
 * Time: 23:05:04
 * To change this template use File | Settings | File Templates.
 */
public class JurassicPark extends JFrame {

    private void init() {
        EngineImpl e = new EngineImpl();
        List<GameObject> gameObjects = GameObjectsFactory.createGameObjects(e);
        GameMap map = GameMapFactory.createMap(gameObjects);
        e.setMap(map);
        final GameField gameField = new GameField(map);
        getContentPane().add(new JScrollPane(gameField));
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        e.addListener(new EngineListener() {
            public void turnMaked() {
                gameField.repaint();
            }
        });
        e.run();
    }

    public static void main(String[] args) {
        JurassicPark jp = new JurassicPark();
        jp.init();
    }

}
