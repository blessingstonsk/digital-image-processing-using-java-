import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Negative {
    public static void main(String[] args) throws IOException {
        BufferedImage colorImage = ImageIO.read(new File("Input.png"));
        BufferedImage negativeImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < colorImage.getHeight(); y++) {
            for (int x = 0; x < colorImage.getWidth(); x++) {
                int rgb = colorImage.getRGB(x, y);
                int invertedRGB = ~rgb;
                negativeImage.setRGB(x, y, invertedRGB);
            }
        }
        ImageIO.write(negativeImage, "png", new File("output_negative.png"));
        System.out.println("Negative image saved to output_negative.png");
    }
}
