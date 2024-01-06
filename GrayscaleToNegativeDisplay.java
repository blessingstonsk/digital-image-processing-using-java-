import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GrayscaleToNegativeDisplay extends JFrame {
    public GrayscaleToNegativeDisplay(BufferedImage image) {
        setSize(image.getWidth(), image.getHeight());
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };
        getContentPane().add(panel);
    }

    public static void main(String[] args) throws IOException {
        BufferedImage grayscaleImage = ImageIO.read(new File("Input.png"));
        BufferedImage negativeImage = new BufferedImage(grayscaleImage.getWidth(), grayscaleImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < grayscaleImage.getHeight(); y++) {
            for (int x = 0; x < grayscaleImage.getWidth(); x++) {
                int grayValue = grayscaleImage.getRGB(x, y) & 0xFF;
                int invertedGrayValue = 255 - grayValue;
                int invertedRGB = (invertedGrayValue << 16) | (invertedGrayValue << 8) | invertedGrayValue;
                negativeImage.setRGB(x, y, invertedRGB);
            }
        }

        SwingUtilities.invokeLater(() -> new GrayscaleToNegativeDisplay(negativeImage).setVisible(true));
    }
}
