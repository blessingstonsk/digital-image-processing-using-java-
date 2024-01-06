import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class BlackWhite {
    public static void main(String[] args) throws IOException {
        BufferedImage colorImage = ImageIO.read(new File("Input.png"));
        BufferedImage bwImage = new BufferedImage(colorImage.getWidth(), colorImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < colorImage.getHeight(); y++) {
            for (int x = 0; x < colorImage.getWidth(); x++) {
                int rgb = colorImage.getRGB(x, y);
                int grayValue = (((rgb >> 16) & 0xFF)+((rgb >> 8) & 0xFF)+(rgb & 0xFF)) / 3;

                bwImage.setRGB(x, y, (grayValue << 16) | (grayValue << 8) | grayValue);
            }
        }

        ImageIO.write(bwImage, "jpg", new File("bw.jpg"));
	System.out.println("Booyah");
    }
}
