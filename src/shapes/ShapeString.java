package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

public class ShapeString extends Shape {
    private String text = "";
    private FontMetrics fm;
    // private JTextField tf;
    private int x1, x2, y1, y2;
    private int width, height;
    private int size;

    public ShapeString(int x1, int y1, int x2, int y2, Color color, float stroke, String text) {
        super(color, stroke, 3);
        // this.tf = tf;
        if (x1 < x2) {
            this.x1 = x1;
            this.x2 = x2;
        } else {
            this.x1 = x2;
            this.x2 = x1;
        }
        if (y1 < y2) {
            this.y1 = y1;
            this.y2 = y2;
        } else {
            this.y1 = y2;
            this.y2 = y1;
        }
        this.width = this.x2 - this.x1;
        this.height = this.y2 - this.y1;
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));

        // 找到最大字号
        if (selected) {
            g2.setFont(new Font("SansSerif", Font.BOLD, size));
        } else {
            g2.setFont(new Font("SansSerif", Font.PLAIN, size));
        }
        fm = g.getFontMetrics();
        while (fm.stringWidth(text) < width && size <= height) {
            size++;
            if (selected) {
                g2.setFont(new Font("SansSerif", Font.BOLD, size));
            } else {
                g2.setFont(new Font("SansSerif", Font.PLAIN, size));
            }
            fm = g.getFontMetrics();
        }
        fm = g.getFontMetrics();
        g.drawString(text, x1, y1 + fm.getAscent());

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

    @Override
    public void addFont() {
        x2 += text.length();
        y2++;
        width += text.length();
        height++;
    }

    @Override
    public void subFont() {
        if (width > 3 && height > 3 * text.length()) {
            x2 -= text.length();
            y2--;
            width -= text.length();
            height--;
        }
    }

}
