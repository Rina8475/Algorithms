package Algorithms;

/* MSD (Most Significant Digit) sorting algorithm */
public class MSD {
    private static int R = 256;         // radix
    private static final int M = 15;    // cutoff for small subarrays
    private static String[] aux;        // auxiliary array for distribution

    /**
     * Sorts an array of Strings using MSD (Most Significant Digit) sorting algorithm.
     * @param a the array to be sorted
     * effects: puts a in sorted order. i.e. a[i] <= a[j]
     *           for all 0 <= i < j < a.length. 
     */
    public static void sort(String[] a) {
        aux = new String[a.length];
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {     // base case
            InsertionSort.sort(a, lo, hi, d);
            return;
        }

        /* where the idx 0 represents the empty string */
        int[] count = new int[R + 2];   
        for (int i = lo; i <= hi; i += 1) {
            count[charAt(a[i], d) + 1] += 1;
        }
        for (int r = 0; r < R + 1; r += 1) {
            count[r + 1] += count[r];
        }
        for (int i = lo; i <= hi; i += 1) {
            aux[count[charAt(a[i], d)]] = a[i];
            count[charAt(a[i], d)] += 1;
        }
        for (int i = lo; i <= hi; i += 1) {
            a[i] = aux[i - lo];
        }
        for (int r = 0; r < R; r += 1) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    private static int charAt(String s, int d) {
        if (d >= s.length()) {
            return 0;
        }
        return s.charAt(d) + 1;
    }
}
