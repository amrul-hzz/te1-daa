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
        long seed = 420L; // do not change seed

//        // contoh penerapan
//        int[] example = new int[]{1, 1, 9, 7 ,8, 10, 1, 2};
//        System.out.print("Initial Array: ");
//        printArray(example);
//        RandomizedShellSort.randomizedShellSort(example);

        generateNumbers(n_small,"small", seed);
        generateNumbers(n_medium,"medium", seed);
        generateNumbers(n_large,"large", seed);

        evaluate();
//        checkSorted();

    }

    public static void evaluate() {
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
    public static void checkSorted() {
        // check if arrays are correctly sorted
        System.out.println(isSortedAscending(small_randomized));
        System.out.println(isSortedAscending(small_sorted));
        System.out.println(isSortedAscending(small_reversed));

        System.out.println(isSortedAscending(medium_randomized));
        System.out.println(isSortedAscending(medium_sorted));
        System.out.println(isSortedAscending(medium_reversed));

        System.out.println(isSortedAscending(large_randomized));
        System.out.println(isSortedAscending(large_sorted));
        System.out.println(isSortedAscending(large_reversed));
    }
    public static void evaluateRandomShellSort(int[] array, String label) {
        // evaluate running time and memory used by Random Shell Sort

        // before
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        double memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();

        RandomizedShellSort.randomizedShellSort(array);

        // after
        long endTime = System.nanoTime();
        double memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // calculations
        long elapsedTimeNano = endTime - startTime;
        double elapsedTimeMillis = (double) elapsedTimeNano / 1_000_000.0;
        double usedMemory = (memoryAfter - memoryBefore)/1024;

        System.out.println("Randomized Shell Sort - " + label + ": " + elapsedTimeMillis + ", " + usedMemory + " KB");
    }
    public static void evaluateMaxHeapSort(int[] array, String label) {
        // evaluate running time and memory used by Max Heap Sort

        // before
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        double memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();

        MaxHeapSort.maxHeapSort(array);

        // after
        long endTime = System.nanoTime();
        double memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // calculations
        long elapsedTimeNano = endTime - startTime;
        double elapsedTimeMillis = (double) elapsedTimeNano / 1_000_000.0;
        double usedMemory = (memoryAfter - memoryBefore)/1024;

        System.out.println("Max Heap Sort - " + label + ": " + elapsedTimeMillis + "ms, " + usedMemory + " KB");
    }
    public static void generateNumbers(int n, String s, long seed) {
        Random random = new Random(seed);

        // generate randomized dataset and export to file
        int[] randomized = new int[n];
        for (int i = 0; i < n; i++) {
            randomized[i] = random.nextInt();
        }
        writeIntegersToFile(randomized, s + "_randomized.txt");

        // generate sorted dataset and export to file
        int[] sorted = randomized.clone();
        Arrays.sort(sorted);
        writeIntegersToFile(sorted, s + "_sorted.txt");

        // generate reverse-sorted dataset and export to file
        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = sorted[n - 1 - i];
        }
        writeIntegersToFile(reversed, s + "_reversed.txt");

        // assign generated datasets
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
        // write all integers in the array to a file
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
    public static boolean isSortedAscending(int[] arr) {
        // check if an array is sorted in an ascending manner
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    public static void printArray(int[] arr){
        // print contents of an array
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}