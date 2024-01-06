import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SimpleForegroundExtraction {

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("Input1.jpg"));
        BufferedImage extractedForeground = extractForeground(originalImage);
        ImageIO.write(extractedForeground, "PNG", new File("foreground.png"));
    }
    private static BufferedImage extractForeground(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Color foregroundColor = new Color(0, 0, 0); // Set the color of the foreground
        int colorThreshold = 100;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = new Color(originalImage.getRGB(x, y));
                int colorDifference = getColorDifference(pixelColor, foregroundColor);
                resultImage.setRGB(x, y, colorDifference < colorThreshold ? pixelColor.getRGB() : Color.TRANSLUCENT);
            }
        }
        return resultImage;
    }
    private static int getColorDifference(Color c1, Color c2) {
        return Math.abs(c1.getRed() - c2.getRed()) + Math.abs(c1.getGreen() - c2.getGreen()) + Math.abs(c1.getBlue() - c2.getBlue());
    }
}
