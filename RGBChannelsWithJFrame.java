import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
public class RGBChannelsWithJFrame {
    public static void main(String[] args) throws IOException {
        BufferedImage colorImage = ImageIO.read(new File("Lenna_(test_image).png"));
        BufferedImage redImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage greenImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage blueImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < colorImage.getHeight(); y++) {
            for (int x = 0; x < colorImage.getWidth(); x++) {
                int color = colorImage.getRGB(x, y);
                redImage.setRGB(x, y, (color & 0xFF) << 16);
                greenImage.setRGB(x, y, (color & 0xFF) << 8);
                blueImage.setRGB(x, y, (color & 0xFF) << 0);
            }
        }
        displayImagesInFrame(colorImage, redImage, greenImage, blueImage);
    }
    private static void displayImagesInFrame(BufferedImage... images) {
        JFrame frame = new JFrame("RGB Channels");
        frame.setLayout(new GridLayout(1, images.length));

        for (BufferedImage image : images) {
            JLabel label = new JLabel(new ImageIcon(image));
            JPanel panel = new JPanel();
            panel.add(label);
            frame.add(panel);
        }
        frame.setSize(800, 400); // Adjust the size as needed
        frame.setVisible(true);
    }
}

