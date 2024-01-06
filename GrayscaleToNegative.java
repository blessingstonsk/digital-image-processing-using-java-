import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class GrayscaleToNegative {
    public static void main(String[] args) throws IOException {
        BufferedImage grayscaleImage = ImageIO.read(new File("Input.png"));
        BufferedImage negativeImage = new BufferedImage(grayscaleImage.getWidth(), grayscaleImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < grayscaleImage.getHeight(); y++) {
            for (int x = 0; x < grayscaleImage.getWidth(); x++) {
                int grayValue = grayscaleImage.getRGB(x, y) & 0xFF; // Extracting the grayscale value
                int invertedGrayValue = 255 - grayValue; // Inverting the grayscale value
                int invertedRGB = (invertedGrayValue << 16) | (invertedGrayValue << 8) | invertedGrayValue;
                negativeImage.setRGB(x, y, invertedRGB);
            }
        }
        ImageIO.write(negativeImage, "png", new File("Negative.png"));
        System.out.println("Negative image saved to GrayscaleToNegative.png");
    }
}
