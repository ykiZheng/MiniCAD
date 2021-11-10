package shapes;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShapeLine extends Shape {
    private int x1, x2, y1, y2;
    private double k, b;

    public ShapeLine(int x1, int y1, int x2, int y2, Color color, float stroke) {
        super(color, stroke, 0);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        k = (double) (y2 - y1) / (x2 - x1);
        b = (double) y1 - k * x1;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean containPoint(int x, int y) {
        // 距离在2内
        double d = Math.abs(k * x + b - y) / Math.sqrt(k * k + 1);
        return d <= 3 ? true : false;
    }

    @Override
    public void move(int newx, int newy) {
        int movex = newx - (x1 + x2) / 2;
        int movey = newy - (y1 + y2) / 2;
        x1 += movex;
        x2 += movex;
        y1 += movey;
        y2 += movey;
        k = (double) (y2 - y1) / (x2 - x1);
        b = (double) y1 - k * x1;

    }

}
