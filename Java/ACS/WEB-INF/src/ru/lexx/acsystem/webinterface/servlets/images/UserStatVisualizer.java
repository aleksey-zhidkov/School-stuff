package ru.lexx.acsystem.webinterface.servlets.images;

import Acme.JPM.Encoders.GifEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 17.03.2006
 * Time: 23:39:10
 */
public class UserStatVisualizer extends HttpServlet {

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/gif");
        java.util.List<Integer> point = getPoints(request);
        int width = 13 * point.size();
        int height = 13;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        drawSquares(point, g, getColor(request));
        GifEncoder gif = new GifEncoder(img, response.getOutputStream());
        gif.encode();
    }

    private java.util.List<Integer> getPoints(HttpServletRequest request) {
        StringTokenizer points = new StringTokenizer(request.getParameter("points"), ",");
        java.util.List<Integer> res = new ArrayList<Integer>();
        while (points.hasMoreTokens()) {
            res.add(new Integer(points.nextToken()));
        }
        return res;
    }

    private void drawSquares(java.util.List<Integer> points, Graphics2D g, Color c) {
        int x = 0;
        int step = 13;
        Font f = new Font("Verdana", Font.BOLD, 10);
        g.setFont(f);
        for (Integer i : points) {
            g.setColor(Color.BLACK);
            g.fillRect(x, 0, 13, 13);
            g.setColor(c);
            g.fillRect(x + 1, 1, 11, 11);
            g.setColor(Color.BLACK);
            if (i.intValue() > 0) {
                g.drawString(i.toString(), x + 4, 10);
            }
            x += step;
        }
    }

    private Color getColor(HttpServletRequest request) {
        return Color.decode(request.getParameter("color"));
    }

}
