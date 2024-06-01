package Algorithms;

/* LSD (Least Significant Digit) sorting algorithm */
public class LSD {

    /**
     * Sorts the array using LSD algorithm.
     * @param a an array of strings to be sorted.
     * @param w a[i].length() = w for all i in [0, a.length-1].
     * effect: put a in sorted order. i.e. a[i] <= a[j]
     *          for all 0 <= i < j < a.length.
     */
    public static void sort(String[] a, int w) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];
        for (int i = w - 1; i >= 0; i -= 1) {
            int[] count = new int[R + 1];
            // compute frequency counts
            for (int k = 0; k < a.length; k += 1) {
                count[a[k].charAt(i) + 1] += 1;
            } 
            // compute cumulates
            for (int k = 0; k < R; k += 1) {
                count[k + 1] += count[k];
            }
            // sort
            for (int k = 0; k < a.length; k += 1) {
                aux[count[a[k].charAt(i)]] = a[k];
                count[a[k].charAt(i)] += 1;
            }
            // copy back
            for (int k = 0; k < a.length; k += 1) {
                a[k] = aux[k];
            }
        }
    }
}
