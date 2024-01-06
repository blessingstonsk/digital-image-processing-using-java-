import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageSharpening {

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("Lenna_(test_image).png"));
            BufferedImage sharpenedImage = sharpenImage(originalImage);
            ImageIO.write(sharpenedImage, "jpg", new File("output_sharpened.jpg"));
            System.out.println("Image sharpened successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage sharpenImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage sharpenedImage = new BufferedImage(width, height, originalImage.getType());

        int[][] laplacianKernel = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int[] rgbValues = applyConvolution(originalImage, i, j, laplacianKernel);
                sharpenedImage.setRGB(i, j, new Color(rgbValues[0], rgbValues[1], rgbValues[2]).getRGB());
            }
        }

        return sharpenedImage;
    }

    private static int[] applyConvolution(BufferedImage image, int x, int y, int[][] kernel) {
        int[] rgbValues = new int[3];	

        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                for (int k = 0; k < 3; k++)
                    rgbValues[k] += new Color(image.getRGB(x + i, y + j)).getRGB() * kernel[i + 1][j + 1] >> 16 - 8 * k;

        for (int i = 0; i < 3; i++)
            rgbValues[i] = Math.min(255, Math.max(0, rgbValues[i]));

        return rgbValues;
    }
}
