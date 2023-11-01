import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Main {
    static int[] small_randomized, small_sorted, small_reversed;
    static int[] medium_randomized, medium_sorted, medium_reversed;
    static int[] large_randomized, large_sorted, large_reversed;


    public static void main(String[] args) {
        int n_small = 512;
        int n_medium = 8192;
        int n_large = 65536;
        long seed = 12345L; // do not change seed

        generateNumbers(n_small,"small", seed);
        generateNumbers(n_medium,"medium", seed);
        generateNumbers(n_large,"large", seed);

        // evaluate randomized shell sort
        evaluateRandomShellSort(small_randomized, "small, random");
        evaluateRandomShellSort(small_sorted, "small, sorted");
        evaluateRandomShellSort(small_reversed, "small, reversed");

        evaluateRandomShellSort(medium_randomized, "medium, random");
        evaluateRandomShellSort(medium_sorted, "medium, sorted");
        evaluateRandomShellSort(medium_reversed, "medium, reversed");

        evaluateRandomShellSort(large_randomized, "large, random");
        evaluateRandomShellSort(large_sorted, "large, sorted");
        evaluateRandomShellSort(large_reversed, "large, reversed");

        // evaluate max heap sort
        evaluateMaxHeapSort(small_randomized, "small, random");
        evaluateMaxHeapSort(small_sorted, "small, sorted");
        evaluateMaxHeapSort(small_reversed, "small, reversed");

        evaluateMaxHeapSort(medium_randomized, "medium, random");
        evaluateMaxHeapSort(medium_sorted, "medium, sorted");
        evaluateMaxHeapSort(medium_reversed, "medium, reversed");

        evaluateMaxHeapSort(large_randomized, "large, random");
        evaluateMaxHeapSort(large_sorted, "large, sorted");
        evaluateMaxHeapSort(large_reversed, "large, reversed");

    }

    public static void evaluateRandomShellSort(int[] array, String label) {
        long startTime = System.nanoTime();
        RandomizedShellSort.randomizedShellSort(array);
        long endTime = System.nanoTime();
        long elapsedTimeNano = endTime - startTime;
        double elapsedTimeMillis = (double) elapsedTimeNano / 1_000_000.0;

        System.out.println("Randomized Shell Sort - " + label + " (ms): " + elapsedTimeMillis);
    }

    public static void evaluateMaxHeapSort(int[] array, String label) {
        long startTime = System.nanoTime();
        MaxHeapSort.maxHeapSort(array);
        long endTime = System.nanoTime();
        long elapsedTimeNano = endTime - startTime;
        double elapsedTimeMillis = (double) elapsedTimeNano / 1_000_000.0;

        System.out.println("Max Heap Sort - " + label + " (ms): " + elapsedTimeMillis);
    }
    public static void generateNumbers(int n, String s, long seed) {
        Random random = new Random(seed);

        int[] randomized = new int[n];
        for (int i = 0; i < n; i++) {
            randomized[i] = random.nextInt();
        }
        writeIntegersToFile(randomized, s + "_randomized.txt");

        int[] sorted = randomized.clone();
        Arrays.sort(sorted);
        writeIntegersToFile(sorted, s + "_sorted.txt");

        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = sorted[n - 1 - i];
        }
        writeIntegersToFile(reversed, s + "_reversed.txt");

        if (s.equals("small")){
            small_randomized = randomized.clone();
            small_sorted = sorted.clone();
            small_reversed = reversed.clone();
        }

        if (s.equals("medium")){
            medium_randomized = randomized.clone();
            medium_sorted = sorted.clone();
            medium_reversed = reversed.clone();
        }

        if (s.equals("large")){
            large_randomized = randomized.clone();
            large_sorted = sorted.clone();
            large_reversed = reversed.clone();
        }
    }
    public static void writeIntegersToFile(int[] integerList, String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Integer number : integerList) {
                bufferedWriter.write(number.toString());
                bufferedWriter.newLine(); // Add a newline after each integer
            }
            System.out.println("Integers have been written to the file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}