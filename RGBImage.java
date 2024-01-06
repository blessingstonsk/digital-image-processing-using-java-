import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class RGBImage {
    public static void main(String args[]) throws IOException {
        BufferedImage image = null;
        File file = null;
        try {
            file = new File("C:\\Users\\Blessington Sunil\\Pictures\\DIP\\Trash\\samplewritesunil.jpg");
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }

        // Process image resizing to fit within the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = screenSize.width - 0;
        int maxHeight = screenSize.height - 0;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int newWidth = imageWidth;
        int newHeight = imageHeight;

        if (imageWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) ((double) newWidth / imageWidth * imageHeight);
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) ((double) newHeight / imageHeight * imageWidth);
        }

        ImageIcon icon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));

        // Create a panel to hold both original and red image
        JPanel imagePanel = new JPanel(new GridLayout(1, 4));
        JLabel originalLabel = new JLabel(icon);
        imagePanel.add(originalLabel);

        // Now, let's create the red version of the image
        BufferedImage redImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                p = (a << 24) | (r << 16) | (0 << 8) | 0;
                redImage.setRGB(x, y, p);
            }
        }

        ImageIcon redIcon = new ImageIcon(redImage);
        JLabel redLabel = new JLabel(redIcon);
        imagePanel.add(redLabel);

        // Now, let's create the green version of the image
        BufferedImage greenImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int g = (p >> 8) & 0xff;
                p = (a << 24) | (0 << 16) | (g << 8) | 0;
                greenImage.setRGB(x, y, p);
            }
        }

        ImageIcon greenIcon = new ImageIcon(greenImage);
        JLabel greenLabel = new JLabel(greenIcon);
        imagePanel.add(greenLabel);


// Now, let's create the Blue version of the image
        BufferedImage blueImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int p = image.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int b = p & 0xff;
                p = (a << 24) | (0 << 16) | (0 << 8) | b;
                blueImage.setRGB(x, y, p);
            }
        }

        ImageIcon blueIcon = new ImageIcon(blueImage);
        JLabel blueLabel = new JLabel(blueIcon);
        imagePanel.add(blueLabel);

        // Create the main frame and add the imagePanel to it
        JFrame frame = new JFrame("Original, Red, Green and Blue Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(imagePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
