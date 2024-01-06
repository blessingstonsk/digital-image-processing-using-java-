import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class MedianFilter {

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("Lenna_(test_image).png"));
        BufferedImage filteredImage = applyMedianFilter(originalImage, 3);
        ImageIO.write(filteredImage, "jpg", new File("output_median.jpg"));
        System.out.println("Median filter applied successfully.");
    }

    private static BufferedImage applyMedianFilter(BufferedImage originalImage, int kernelSize) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, originalImage.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] medianPixel = getMedianPixel(originalImage, i, j, kernelSize);
                filteredImage.setRGB(i, j, new Color(medianPixel[0], medianPixel[1], medianPixel[2]).getRGB());
            }
        }

        return filteredImage;
    }

    private static int[] getMedianPixel(BufferedImage image, int x, int y, int kernelSize) {
        int w = image.getWidth(), h = image.getHeight();
        int[] redValues = new int[kernelSize * kernelSize];
        int[] greenValues = new int[kernelSize * kernelSize];
        int[] blueValues = new int[kernelSize * kernelSize];
        int count = 0;

        for (int i = x - kernelSize / 2; i <= x + kernelSize / 2; i++) {
            for (int j = y - kernelSize / 2; j <= y + kernelSize / 2; j++) {
                if (i >= 0 && i < w && j >= 0 && j < h) {
                    Color color = new Color(image.getRGB(i, j));
                    redValues[count] = color.getRed();
                    greenValues[count] = color.getGreen();
                    blueValues[count] = color.getBlue();
                    count++;
                }
            }
        }

        Arrays.sort(redValues, 0, count);
        Arrays.sort(greenValues, 0, count);
        Arrays.sort(blueValues, 0, count);

        int medianIndex = count / 2;

        return new int[]{redValues[medianIndex], greenValues[medianIndex], blueValues[medianIndex]};
    }
}
