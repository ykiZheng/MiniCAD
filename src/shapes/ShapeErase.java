package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShapeErase extends Shape {
    private int x1, x2, y1, y2;
    private int width, height;

    public ShapeErase(int x1, int y1, int x2, int y2, Color color, float stroke) {
        super(color, stroke, 5);
        this.x1 = x1 < x2 ? x1 : x2;
        this.y1 = y1 < y2 ? y1 : y2;
        this.x2 = x1 > x2 ? x1 : x2;
        this.y2 = y1 > y2 ? y1 : y2;
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));

        g.clearRect(x1, y1, width, height);
    }

    @Override
    public boolean containPoint(int x, int y) {
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void move(int newx, int newy) {
        int movex = newx - (x1 + x2) / 2;
        int movey = newy - (y1 + y2) / 2;
        x1 += movex;
        x2 += movex;
        y1 += movey;
        y2 += movey;

    }

}
