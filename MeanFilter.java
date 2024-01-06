import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class MeanFilter {
    public static void main(String[] args) throws IOException {
        BufferedImage original = ImageIO.read(new File("Lenna_(test_image).png"));
        BufferedImage filtered = applyMeanFilter(original, 3);
        ImageIO.write(filtered, "jpg", new File("output.jpg"));
        System.out.println("Mean filter applied successfully.");
    }
    private static BufferedImage applyMeanFilter(BufferedImage original, int kernelSize) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage filtered = new BufferedImage(width, height, original.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] meanPixel = getMeanPixel(original, i, j, kernelSize);
                filtered.setRGB(i, j, new Color(meanPixel[0], meanPixel[1], meanPixel[2]).getRGB());
            }
        }
        return filtered;
    }

    private static int[] getMeanPixel(BufferedImage image, int x, int y, int kernelSize) {
        int w = image.getWidth();
        int h = image.getHeight();
        int count = 0;
        int[] sum = {0, 0, 0};

        for (int i = x - kernelSize / 2; i <= x + kernelSize / 2; i++) {
            for (int j = y - kernelSize / 2; j <= y + kernelSize / 2; j++) {
                if (i >= 0 && i < w && j >= 0 && j < h) {
                    Color c = new Color(image.getRGB(i, j));
                    sum[0] += c.getRed();
                    sum[1] += c.getGreen();
                    sum[2] += c.getBlue();
                    count++;
                }
            }
        }

        return new int[]{sum[0] / count, sum[1] / count, sum[2] / count};
    }
}
