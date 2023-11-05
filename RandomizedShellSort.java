import java.util.*;

/*
 * Taken from:
 * https://dl.acm.org/doi/pdf/10.1145/2049697.2049701?casa_token=Q3m5kUD8cXwAAAAA:W74e11vozONFwdODsiFG213H5SqLOzjM9a-bYPENfqnuP13NG5l3gZOMWCsKWgO1zKEZW8H47seNoA
 */
public class RandomizedShellSort {
    public static final int C = 1; // number of region compare-exchange repetitions

    public static void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void compareExchange(int[] a, int i, int j) {
        if (((i < j) && (a[i] > a[j])) || ((i > j) && (a[i] < a[j]))) {
            exchange(a, i, j);
        }
    }

    public static void permuteRandom(int[] a, MyRandom rand) {
        // use the Knuth random permutation algorithm
        for (int i = 0; i < a.length; i++) {
            exchange(a, i, rand.nextInt(a.length - i) + i);
        }
    }

    // compare-exchange two regions of length offset each
    public static void compareRegions(int[] a, int s, int t, int offset, MyRandom rand) {
        int[] mate = new int[offset]; // index offset array
        for (int count = 0; count < C; count++) { // do C region compare-exchanges
            for (int i = 0; i < offset; i++) mate[i] = i;
            permuteRandom(mate, rand); // comment this out to get a deterministic Shellsort
            for (int i = 0; i < offset; i++) {
                compareExchange(a, s + i, t + mate[i]);
            }
        }

//        System.out.print("\t\tresult of compareRegion: ");
//        Main.printArray(a);
    }

    public static void randomizedShellSort(int[] a) {
        int n = a.length;
        MyRandom rand = new MyRandom(); // assuming MyRandom is some custom random number generator

        for (int offset = n / 2; offset > 0; offset /= 2) {
            // compare-exhange up
//            System.out.println("Offset = " + offset);
            for (int i = 0; i < n - offset; i+= offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
//            System.out.print("\tAfter compare-exchange up: ");
//            Main.printArray(a);

            // compare-exchange down
            for (int i = n - offset; i >= offset; i -= offset) {
                compareRegions(a, i - offset, i, offset, rand);
            }
//            System.out.print("\tAfter compare-exchange down: ");
//            Main.printArray(a);

            // compare 3 hops up
            for (int i = 0; i < n - 3 * offset; i += offset) {
                compareRegions(a, i, i + 3 * offset, offset, rand);
            }
//            System.out.print("\tAfter compare 3 hops up: ");
//            Main.printArray(a);

            // compare 2 hops up
            for (int i = 0; i < n - 2 * offset; i += offset) {
                compareRegions(a, i, i + 2 * offset, offset, rand);
            }
//            System.out.print("\tAfter compare 2 hops up: ");
//            Main.printArray(a);

            // compare odd-even regions
            for (int i = 0; i < n; i += 2 * offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
//            System.out.print("\tAfter compare odd-even regions: ");
//            Main.printArray(a);

            // compare even-odd regions
            for (int i = offset; i < n - offset; i += 2 * offset) {
                compareRegions(a, i, i + offset, offset, rand);
            }
//            System.out.print("\tAfter compare even-odd regions: ");
//            Main.printArray(a);
//
//            System.out.println();
        }
    }

}

// Assuming MyRandom is some custom random number generator class
class MyRandom {
    Random r = new Random();

    public int nextInt(int bound) {
        return r.nextInt(bound);
    }
}
