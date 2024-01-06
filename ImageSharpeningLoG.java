import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageSharpeningLoG {

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("Lenna_(test_image).png"));
            BufferedImage sharpenedImage = sharpenImage(originalImage);
            ImageIO.write(sharpenedImage, "jpg", new File("output_sharpened_log.jpg"));
            System.out.println("Image sharpened successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage sharpenImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage sharpenedImage = new BufferedImage(width, height, originalImage.getType());

        int[][] logKernel = {
                {0, 0, -1, 0, 0},
                {0, -1, -2, -1, 0},
                {-1, -2, 16, -2, -1},
                {0, -1, -2, -1, 0},
                {0, 0, -1, 0, 0}
        };

        for (int i = 2; i < width - 2; i++) {
            for (int j = 2; j < height - 2; j++) {
                int[] rgbValues = applyLoG(originalImage, i, j, logKernel);
                sharpenedImage.setRGB(i, j, new Color(rgbValues[0], rgbValues[1], rgbValues[2]).getRGB());
            }
        }

        return sharpenedImage;
    }

    private static int[] applyLoG(BufferedImage image, int x, int y, int[][] kernel) {
        int[] rgbValues = new int[3];

        for (int i = -2; i <= 2; i++)
            for (int j = -2; j <= 2; j++)
                for (int k = 0; k < 3; k++)
                    rgbValues[k] += new Color(image.getRGB(x + i, y + j)).getRGB() * kernel[i + 2][j + 2] >> 16 - 8 * k;

        for (int i = 0; i < 3; i++)
            rgbValues[i] = Math.min(255, Math.max(0, rgbValues[i]));

        return rgbValues;
    }
}
