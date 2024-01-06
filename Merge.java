import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Merge {
    public static void main(String[] args) {
        String foregroundImagePath = "C:\\Users\\Blessington Sunil\\Pictures\\Camera Roll\\Tiger.png";
        String backgroundImagePath = "C:\\Users\\Blessington Sunil\\Pictures\\Camera Roll\\bg.jpg";
        String outputPath = "C:\\Users\\Blessington Sunil\\Pictures\\DIP\\Tigermerge.jpg";

        try {
            // Read the background and foreground images using BufferedImage
            BufferedImage backgroundImage = ImageIO.read(new File(backgroundImagePath));
            BufferedImage foregroundImage = ImageIO.read(new File(foregroundImagePath));

            // Get the width and height of the images
            int width = Math.min(backgroundImage.getWidth(), foregroundImage.getWidth());
            int height = Math.min(backgroundImage.getHeight(), foregroundImage.getHeight());

            // Merge the images
            BufferedImage mergedImage = mergeImages(backgroundImage, foregroundImage, width, height);

            // Save the merged image
            File outputFile = new File(outputPath);
            ImageIO.write(mergedImage, "PNG", outputFile);
            System.out.println("Images merged and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage mergeImages(BufferedImage background, BufferedImage foreground, int width, int height) {
        BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int backgroundRGB = background.getRGB(x, y);
                int foregroundRGB = foreground.getRGB(x, y);

                // Extract alpha channel (transparency) from the foreground image
                int fgAlpha = (foregroundRGB >> 24) & 0xFF;

                if (fgAlpha == 0) {
                    // Fully transparent pixel in the foreground, use the background pixel
                    mergedImage.setRGB(x, y, backgroundRGB);
                } else {
                    // Extract RGB components from the background and foreground images
                    int bgRed = (backgroundRGB >> 16) & 0xFF;
                    int bgGreen = (backgroundRGB >> 8) & 0xFF;
                    int bgBlue = backgroundRGB & 0xFF;

                    int fgRed = (foregroundRGB >> 16) & 0xFF;
                    int fgGreen = (foregroundRGB >> 8) & 0xFF;
                    int fgBlue = foregroundRGB & 0xFF;

                    // Calculate the weighted RGB values based on the alpha value
                    int mergedRed = (fgAlpha * fgRed + (255 - fgAlpha) * bgRed) / 255;
                    int mergedGreen = (fgAlpha * fgGreen + (255 - fgAlpha) * bgGreen) / 255;
                    int mergedBlue = (fgAlpha * fgBlue + (255 - fgAlpha) * bgBlue) / 255;

                    // Combine the RGB components to create the merged color
                    int mergedColor = (fgAlpha << 24) | (mergedRed << 16) | (mergedGreen << 8) | mergedBlue;

                    // Set the pixel color in the merged image
                    mergedImage.setRGB(x, y, mergedColor);
                }
            }
        }

        return mergedImage;
    }
}
