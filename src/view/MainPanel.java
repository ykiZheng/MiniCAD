package view;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.DrawListener;
import shapes.Shape;

public class MainPanel extends JPanel {
    private int SRC_WIDTH = 800;
    private int SRC_HEIGHT = 600;
    private int HEADER_WIDTH = 40;
    private int HEADER_HEIGHT = 40;

    private JFrame frame;
    private JRadioButton[] commandBt;
    private JFileChooser fc;
    public ArrayList<Shape> shapeArray = new ArrayList<Shape>();
    private DrawListener dl = new DrawListener();
    public Shape tempShape = null;
    public int selIndex = -1;

    /**
     * 初始化界面
     */
    public void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("MiniCAD");
        frame.setSize(SRC_WIDTH, SRC_HEIGHT);
        frame.setLocationRelativeTo(null); // 窗体居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fc = new JFileChooser(new File("."));
        fc.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp"));
        dl.setJFileChooser(fc);

        makeMenuBar(); // 菜单栏
        makeToolBar(); // 工具栏
        this.setBackground(Color.white); // 画图面板
        this.addMouseListener(dl);
        this.addMouseMotionListener(dl);
        this.addKeyListener(dl);
        frame.add(this, BorderLayout.CENTER);

        // 显示
        frame.setVisible(true);
        dl.setFrame(frame);
        dl.setPanel(this);

    }

    /**
     * 按钮样式
     * 
     * @param cmd         命令
     * @param pos         未选中时显示图片路径
     * @param selectedPos 选中时显示图片路径
     * @return
     */
    private JRadioButton setButton(String cmd, URL pos, URL selectedPos) {

        ImageIcon imgIcon = new ImageIcon(pos);
        imgIcon.getImage();
        Image temp = imgIcon.getImage().getScaledInstance(HEADER_WIDTH, HEADER_HEIGHT, Image.SCALE_DEFAULT);
        imgIcon = new ImageIcon(temp);

        // 鼠标选中时的按钮图片
        ImageIcon selectedIcon = new ImageIcon(selectedPos);
        selectedIcon.getImage();
        Image temp2 = selectedIcon.getImage().getScaledInstance(HEADER_WIDTH, HEADER_HEIGHT, Image.SCALE_DEFAULT);

        selectedIcon = new ImageIcon(temp2);

        JRadioButton comBt = new JRadioButton(cmd);
        comBt.setIcon(imgIcon);
        comBt.setSelectedIcon(selectedIcon);
        comBt.setVerticalTextPosition(JButton.BOTTOM);
        comBt.setHorizontalTextPosition(JButton.CENTER);
        comBt.setContentAreaFilled(false); // 去边框
        // commandBt[i].setMargin(new Insets(0,0,0,0));
        comBt.setPreferredSize(new Dimension(HEADER_WIDTH, HEADER_HEIGHT));
        return comBt;
    }

    /**
     * 菜单栏
     */
    private void makeMenuBar() {
        JMenuBar jmBar = new JMenuBar();
        String[] menus = { "文件", "帮助" };
        String[][] subMenu = { { "打开", "保存" }, { "关于" } };
        for (int i = 0; i < menus.length; i++) {
            JMenu jmenu = new JMenu(menus[i]);
            for (int j = 0; j < subMenu[i].length; j++) {
                JMenuItem jmt = new JMenuItem(subMenu[i][j]);
                jmt.addActionListener(dl);
                jmenu.add(jmt);
            }
            jmBar.add(jmenu);
        }
        frame.add(jmBar, BorderLayout.NORTH);

    }

    /**
     * 工具栏
     */
    private void makeToolBar() {
        String[] commands = { "选中", "直线", "矩形", "圆", "文字", "橡皮擦" };
        String[] pos = { "/icon/pen", "/icon/line", "/icon/rectangle", "/icon/oval", "/icon/text", "/icon/erase" };
        // 命令面板
        URL[] posUrl = new URL[pos.length];
        URL[] selPosUrl = new URL[pos.length];
        for (int i = 0; i < pos.length; i++) {
            posUrl[i] = MainPanel.class.getResource(pos[i] + ".png");
            selPosUrl[i] = MainPanel.class.getResource(pos[i] + "_selected.png");
        }

        JPanel jp1 = new JPanel();
        jp1.setLayout(new GridLayout(7, 1));
        jp1.setPreferredSize(new Dimension(HEADER_WIDTH + HEADER_WIDTH / 2, SRC_HEIGHT));
        frame.add(jp1, BorderLayout.WEST);

        // 按钮对象
        ButtonGroup jrGroup = new ButtonGroup();
        commandBt = new JRadioButton[commands.length];
        for (int i = 0; i < commands.length; i++) {
            commandBt[i] = setButton(commands[i], posUrl[i], selPosUrl[i]);
            jrGroup.add(commandBt[i]);
            jp1.add(commandBt[i]);
        }
        commandBt[0].setSelected(true);

        // 颜色选择
        JButton color = new JButton(" ");
        color.setBackground(Color.gray);
        color.setPreferredSize(new Dimension(HEADER_HEIGHT, HEADER_HEIGHT));
        color.setVerticalTextPosition(JButton.BOTTOM);
        color.setHorizontalTextPosition(JButton.CENTER);
        jp1.add(color);

        // 监视器
        for (JRadioButton bt : commandBt) {
            bt.addActionListener(dl);
            bt.addKeyListener(dl);
        }
        color.addActionListener(dl);

        dl.setColorBt(color);
        dl.setRadioGroup(jrGroup);

    }

    public void paint(Graphics g) {
        super.paint(g);
        for (Shape l : shapeArray) {
            l.draw(g);
        }
        if (tempShape != null) {
            tempShape.draw(g);
        }
    }

    public void addShape(Shape s) {
        shapeArray.add(s);
    }

    public void setShpae(int index, Shape s) {
        if (index >= 0)
            shapeArray.set(index, s);
    }

    public int getNum() {
        return shapeArray.size();
    }

}