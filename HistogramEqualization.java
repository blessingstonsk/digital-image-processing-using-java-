import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HistogramEqualization extends JPanel {
    private int[] histogram = new int[256];
    private int[] equalizedHistogram = new int[256];

    public HistogramEqualization(BufferedImage image) {
        calculateHistogram(image);
        equalizeHistogram();
    }

    private void calculateHistogram(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                histogram[new Color(image.getRGB(x, y)).getRed()]++;

    }

    private void equalizeHistogram() {
        int totalPixels = java.util.Arrays.stream(histogram).sum();
        for (int i = 0, cumulativeSum = 0; i < 256; cumulativeSum += histogram[i++])
            equalizedHistogram[i] = (int) ((double) cumulativeSum / totalPixels * 255);
    }

    private BufferedImage applyEqualization(BufferedImage image) {
        BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++)
                resultImage.setRGB(x, y, new Color(equalizedHistogram[new Color(image.getRGB(x, y)).getRed()]).getRGB());
        return resultImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxCount = java.util.Arrays.stream(histogram).max().getAsInt();
        int histogramHeight = 100;

        for (int i = 0; i < 256; i++) {
            int barHeight = (int) ((double) histogram[i] / maxCount * histogramHeight);
            g.setColor(Color.black);
            g.drawLine(i, histogramHeight, i, histogramHeight - barHeight);
        }
    }

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("Input5.jpg"));
            HistogramEqualization histogramEqualization = new HistogramEqualization(originalImage);

            BufferedImage equalizedImage = histogramEqualization.applyEqualization(originalImage);

            JFrame frame = new JFrame("Histogram Equalization");

            frame.setLayout(new GridLayout(1, 3));

            frame.add(new JLabel(new ImageIcon(originalImage)), BorderLayout.WEST);
            frame.add(new JLabel(new ImageIcon(equalizedImage)), BorderLayout.CENTER);
            frame.add(new HistogramEqualization(histogramEqualization.applyEqualization(originalImage)), BorderLayout.EAST);

            frame.setSize(900, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
