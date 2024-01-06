import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Foreground {
    public static void main(String[] args) {
        try {
            // Load the original image and the background model image
            BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\Blessington Sunil\\Pictures\\DIP\\Tigermerge.jpg"));
            BufferedImage backgroundModel = ImageIO.read(new File("C:\\Users\\Blessington Sunil\\Pictures\\Camera Roll\\bg.jpg"));

            // Convert images to matrices
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            int[][] originalMatrix = imageToMatrix(originalImage, width, height);
            int[][] backgroundMatrix = imageToMatrix(backgroundModel, width, height);

            // Compute the absolute difference between the original and background matrices
            int[][] differenceMatrix = computeDifference(originalMatrix, backgroundMatrix, width, height);

            // Threshold the difference matrix to create a binary mask
            int threshold = 25; // You can experiment with different threshold values
            int[][] binaryMask = thresholdMatrix(differenceMatrix, width, height, threshold);

            // Create a new image using the binary mask as the alpha channel
            BufferedImage foregroundImage = createForegroundImage(originalImage, binaryMask, width, height);


            // Display the resulting foreground image in a JFrame window
            showImageInFrame(foregroundImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert a BufferedImage to a 2D matrix
    private static int[][] imageToMatrix(BufferedImage image, int width, int height) {
        int[][] matrix = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int gray = (red + green + blue) / 3; // Grayscale value
                matrix[y][x] = gray;
            }
        }
        return matrix;
    }

    // Compute the absolute difference between two matrices
    private static int[][] computeDifference(int[][] matrix1, int[][] matrix2, int width, int height) {
        int[][] difference = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                difference[y][x] = Math.abs(matrix1[y][x] - matrix2[y][x]);
            }
        }
        return difference;
    }

    // Threshold a matrix to create a binary mask using matrix subtraction
    private static int[][] thresholdMatrix(int[][] matrix, int width, int height, int threshold) {
        int[][] binaryMask = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                binaryMask[y][x] = (matrix[y][x] > threshold) ? 255 : 0;
            }
        }
        return binaryMask;
    }

    // Create a new image using the binary mask as the alpha channel
    private static BufferedImage createForegroundImage(BufferedImage originalImage, int[][] binaryMask, int width, int height) {
        BufferedImage foregroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                int alpha = binaryMask[y][x];
                int newRgb = (alpha << 24) | (rgb & 0x00FFFFFF); // Set alpha channel
                foregroundImage.setRGB(x, y, newRgb);
            }
        }
        return foregroundImage;
    }

    private static void showImageInFrame(BufferedImage image) {
        // Create a JFrame to display the image
        JFrame frame = new JFrame("Foreground Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Check if the image width or height exceeds the screen size and adjust the window size accordingly
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = screenSize.width - 100;
        int maxHeight = screenSize.height - 100;
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int newWidth = imageWidth;
        int newHeight = imageHeight;

        if (imageWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) ((double) newWidth / imageWidth * imageHeight);
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) ((double) newHeight / imageHeight * imageWidth);
        }

        ImageIcon icon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(icon);

        frame.getContentPane().add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
