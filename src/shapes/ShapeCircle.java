package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShapeCircle extends Shape {

    private int radius;
    private int startX, startY;
    public ShapeCircle(int x1, int y1, int x2, int y2, Color color, float stroke) {
        super(color, stroke, 2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        radius = (int) Math.sqrt(width * width + height * height);
        startX = x1;
        startY = y1;

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawOval(startX - radius, startY - radius, radius * 2, radius * 2);
    }
    
    @Override
    public boolean containPoint(int x, int y) {
        if ((x - startX) * (x - startX) + (y - startY) * (y - startY) < radius * radius) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void move(int newx, int newy) {
        startX += newx-startX;
        startY += newy-startY;
    }

}
