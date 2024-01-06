import java.util.Arrays;

public class StringDiscreteWaveletTransform {

    public static void main(String[] args) {
        String input = "hello";
        int[] signal = convertStringToSignal(input);
        System.out.println("Original Signal: " + Arrays.toString(signal));
        int[] transformedSignal = dwtHaar(signal);
        System.out.println("Transformed Signal: " + Arrays.toString(transformedSignal));
    }

    private static int[] convertStringToSignal(String input) {
        return input.chars().toArray();
    }

    private static int[] dwtHaar(int[] signal) {
        int n = signal.length;
        int[] transformedSignal = new int[n];

        for (int i = 0; i < n / 2; i++) {
            int j = i * 2;
            transformedSignal[i] = (signal[j] + signal[j + 1]) / 2;
            transformedSignal[i + n / 2] = (signal[j] - signal[j + 1]) / 2;
        }

        return transformedSignal;
    }
}
