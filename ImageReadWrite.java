import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReadWrite {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("D:\\III Semester\\Digital Image Processing\\Java\\Images\\Input3.jpg"));
        ImageIO.write(image, "PNG", new File("outputread1.png"));
        System.out.println("Image has been read successfully");
    }
}
