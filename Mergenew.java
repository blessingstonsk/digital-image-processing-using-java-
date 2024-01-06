import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mergenew {
    public static void main(String[] args) throws IOException {
        BufferedImage foregroundImage = ImageIO.read(new File("fg.png"));
        BufferedImage backgroundImage = ImageIO.read(new File("bg.jpg"));
        BufferedImage mergedImage = new BufferedImage(backgroundImage.getWidth(),backgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mergedImage.createGraphics();
        g2d.drawImage(backgroundImage, 0, 0, null);
        g2d.drawImage(foregroundImage, 100, 100, null);
        g2d.dispose();
        ImageIO.write(mergedImage, "PNG", new File("out23.png"));
    }
}
