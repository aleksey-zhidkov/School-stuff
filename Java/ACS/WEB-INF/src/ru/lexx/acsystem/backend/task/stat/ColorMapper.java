package ru.lexx.acsystem.backend.task.stat;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexey Zhidkov
 */
public class ColorMapper {

    private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();

    public ColorMapper(int colors) {
        int r = 255;
        int g = 0;
        int b = 0;
        int shift = 1023 / (colors + 1);
        int p = 1;
        for (int i = 0; i < colors; i++) {
            colorMap.put(new Integer(i), new Color(r, g, b));
            switch (p) {
                case 1:
                    r -= shift;
                    g += shift;
                    if (r <= 0) {
                        b = -r;
                        r = 0;
                        g = 255;
                        p = 2;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    break;
                case 2:
                    g -= shift;
                    b += shift;
                    if (g <= 0) {
                        r = -g;
                        g = 0;
                        b = 255;
                        p = 3;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    break;
                case 3:
                    b -= shift;
                    r += shift;
                    if (b <= 0) {
                        g = -b;
                        b = 0;
                        r = 255;
                        p = 1;
                    }
                    if (r > 255) {
                        r = 255;
                    }
                    break;
            }
        }
    }

    public Color getColor(int i) {
        return colorMap.get(new Integer(i));
    }
}