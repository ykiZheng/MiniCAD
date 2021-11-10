package utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import view.MainPanel;
import shapes.Shape;

public class FileOp {

    public void saveFile(String fpath, MainPanel jp) throws IOException {
        BufferedImage im = makePanel(jp);
        File f = new File(fpath+".png");
        ImageIO.write(im, "png", f);

        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fpath+".miniCAD"));
        os.writeObject(jp.shapeArray);
        os.close();

    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<Shape> openFile(String fpath) throws IOException, ClassNotFoundException{
        ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(fpath+".miniCAD"));
        ArrayList<Shape> n =  (ArrayList<Shape>)inStream.readObject();
        inStream.close();
        return n;
    }

    private BufferedImage makePanel(JPanel panel) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        panel.paint(g2);
        return bi;
    }


}
