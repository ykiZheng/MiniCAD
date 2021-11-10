package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/*基类 */
public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Color color;
    protected Color tempColor = Color.red;
    protected float stroke;
    protected float thinStroke;
    protected float thickStroke = 7;
    // 0: 直线, 1:矩形, 2:圆, 3:文字 4:自由画笔 5: 橡皮擦 6:图像
    protected int type;
    protected boolean selected = false;

    Shape(Color color, float stroke, int type) {
        this.color = color;
        this.tempColor = color;
        this.stroke = stroke;
        this.thinStroke = stroke;
        this.type = type;

    }

    /* 定义一个抽象方法，用来画图 */
    public abstract void draw(Graphics g);

    public abstract boolean containPoint(int x, int y);

    public abstract void move(int newx, int newy);

    public void setColor(Color c) {
        this.color = c;
        this.tempColor = color;
    }

    public void addFont() {
    };

    public void subFont() {
    };

    public void setSelected(boolean c) {
        selected = c;
        if (selected) {
            setThicker();
        } else {
            setThinner();
        }
    }

    private void setThicker() {
        if (type == 3) {
            color = Color.red;
        }
        stroke = thickStroke;
    }

    private void setThinner() {
        if (type == 3) {
            setColor(tempColor);
        }
        stroke = thinStroke;
    }

    public void addStroke() {
        if (thickStroke < 20) {
            thinStroke += 2;
            thickStroke += 2;
        }
    }

    public void subStroke() {
        if (thinStroke > 4) {
            thinStroke -= 2;
            thickStroke -= 2;
        }
    }

    public int getType() {
        return type;
    }
}
