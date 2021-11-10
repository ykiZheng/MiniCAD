package utils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.*;

import view.MainPanel;
import shapes.*;

// 鼠标监听
public class DrawListener implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
    private boolean hasSelected = false;
    private int mouseX, mouseY;
    private int lastX, lastY;
    private Color color;
    private JFrame frame;
    private ButtonGroup jg;
    private JButton colorBt;
    private MainPanel jp;
    private String command;
    private JFileChooser fc;

    public DrawListener() {
        this.color = Color.gray;
        this.command = "选中";

    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setColorBt(JButton colorBt) {
        this.colorBt = colorBt;
    }

    public void setRadioGroup(ButtonGroup bg) {
        this.jg = bg;
    }

    public void setPanel(MainPanel jp) {
        this.jp = jp;
    }

    public void setJFileChooser(JFileChooser fc) {
        this.fc = fc;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (command.equals("直线")) {
            jp.tempShape = new ShapeLine(lastX, lastY, mouseX, mouseY, color, 2);
            jp.repaint();

        } else if (command.equals("圆")) {
            jp.tempShape = new ShapeCircle(lastX, lastY, mouseX, mouseY, color, 2);
            jp.repaint();

        } else if (command.equals("矩形")) {
            jp.tempShape = new ShapeRect(lastX, lastY, mouseX, mouseY, color, 2);
            jp.repaint();

        } else if (command.equals("橡皮擦")) {
            jp.tempShape = new ShapeErase(lastX, lastY, mouseX, mouseY, color, 10);
            jp.repaint();

        } else if (command.equals("文字")) {
            jp.tempShape = new ShapeString(lastX, lastY, mouseX, mouseY, color, 2, "等待输入");
            jp.repaint();

        } else if (command.equals("选中")) {
            jp.tempShape = null;

            if (hasSelected && jp.selIndex != -1) {
                jp.shapeArray.get(jp.selIndex).move(e.getX(), e.getY());
                jp.repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
        if (command.equals("选中")) {
            int targetShape = -1;
            for (int i = 0; i < jp.shapeArray.size(); i++) {
                jp.shapeArray.get(i).setSelected(false);
                if (jp.shapeArray.get(i).containPoint(e.getX(), e.getY())) {
                    targetShape = i;
                }
            }
            if (targetShape != -1) {
                jp.selIndex = targetShape;
                jp.shapeArray.get(jp.selIndex).setSelected(true);
                hasSelected = true;
            } else {
                jp.selIndex = -1;
            }
            jp.repaint();
        } else if (command.equals("文字")) {
            jp.addShape(new ShapeString(lastX, lastY, lastX, lastY, color, 2, "等待输入"));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (jp.tempShape != null) {
            jp.addShape(jp.tempShape);
            jp.tempShape = null;
        }
        if (command.equals("文字")) {
            JFrame textFrame = new JFrame("输入窗口");
            // textFrame.setLayout(null);
            textFrame.setSize(400, 150);
            textFrame.setLocationRelativeTo(null);
            JTextField tf = new JTextField();
            tf.setSize(300, 30);
            JButton btnOK = new JButton("确认");
            btnOK.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = tf.getText();
                    ShapeString shape = new ShapeString(lastX, lastY, mouseX, mouseY, color, 2, text);
                    jp.setShpae(jp.getNum() - 1, shape);
                    ShapeString s2 = new ShapeString(0, 0, 0, 0, color, 2, "");
                    jp.addShape(s2);
                    textFrame.dispose();
                    jp.repaint();
                    lastX = mouseX;
                    lastY = mouseY;
                }

            });
            textFrame.add(tf, BorderLayout.CENTER);
            textFrame.add(btnOK, BorderLayout.SOUTH);
            textFrame.setVisible(true);
            return;
        }

        lastX = mouseX;
        lastY = mouseY;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        hasSelected = false;
        if (cmd.equals(" ")) {
            Color jcolor = JColorChooser.showDialog(frame, "颜色选择", color);
            if (jcolor != null) {
                colorBt.setBackground(jcolor);
                color = jcolor;
                if (jp.selIndex != -1) {
                    jp.shapeArray.get(jp.selIndex).setColor(color);
                    jp.repaint();

                }
            }
        } else if (cmd.equals("保存")) {
            if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String filePath = f.getPath();
                try {
                    FileOp o = new FileOp();
                    o.saveFile(filePath, jp);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else if (cmd.equals("打开")) {
            if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                String filePath = f.getAbsolutePath();
                filePath = filePath.substring(0, filePath.length() - 4);
                FileOp o = new FileOp();
                try {
                    jp.shapeArray = o.openFile(filePath);
                    jp.repaint();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        } else if (cmd.equals("关于")) {
            JFrame aboutFrame = new JFrame("关于本程序");
            aboutFrame.setSize(400, 150);
            aboutFrame.setLocationRelativeTo(null);
            aboutFrame.setBackground(Color.white);
            JLabel tf = new JLabel();
            tf.setSize(300, 30);
            tf.setHorizontalAlignment(SwingConstants.CENTER);
            tf.setText(
                    "<html><head><p align=\"center\">MiniCAD</p></head><body><center><p align=\"left\">数据版本：v1.0.0<br/>制作者：Zheng Yuqi<br/>完成时间：2021/11/10</p></body></html>");
            JButton btnOK = new JButton("关闭");
            btnOK.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    aboutFrame.dispose();
                    jp.repaint();
                    lastX = mouseX;
                    lastY = mouseY;
                }

            });
            aboutFrame.add(tf, BorderLayout.CENTER);
            aboutFrame.add(btnOK, BorderLayout.SOUTH);
            aboutFrame.setVisible(true);
        } else {
            if (cmd.equals("橡皮擦")) {
                color = Color.white;
                colorBt.setBackground(color);
            }
            command = cmd;
            jp.selIndex = -1;

            Enumeration<AbstractButton> allRadioButton = jg.getElements();
            while (allRadioButton.hasMoreElements()) {
                JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
                if (temp.isSelected()) {
                    temp.setForeground(Color.blue);
                } else {
                    temp.setForeground(Color.black);

                }
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char k = e.getKeyChar();
        if (hasSelected && jp.selIndex != -1 && (k == 'm' || k == 'n')) {

            if (k == 'm') {
                jp.shapeArray.get(jp.selIndex).addStroke();
                if (jp.shapeArray.get(jp.selIndex).getType() == 3) {
                    jp.shapeArray.get(jp.selIndex).addFont();
                }
            } else {
                jp.shapeArray.get(jp.selIndex).subStroke();
                if (jp.shapeArray.get(jp.selIndex).getType() == 3) {
                    jp.shapeArray.get(jp.selIndex).subFont();
                }
            }
            jp.shapeArray.get(jp.selIndex).setSelected(true);
            jp.repaint();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}