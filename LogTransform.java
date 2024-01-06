import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class LogTransform {
    public static void main(String[] args) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File("Lenna_(test_image).png"));
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        double c = 255 / Math.log(256);
        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
                int r = (int) (c * Math.log(1 + (rgb >> 16) & 0xFF));
                int g = (int) (c * Math.log(1 + (rgb >> 8) & 0xFF));
                int b = (int) (c * Math.log(1 + rgb & 0xFF));
                outputImage.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        ImageIO.write(outputImage, "jpg", new File("output_log.jpg"));
        System.out.println("Log-transformed image saved to output_log.jpg");
    }
}
