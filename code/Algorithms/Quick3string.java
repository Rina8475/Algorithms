package Algorithms;

public class Quick3string extends Sort {
    /**
     * Sorts the array of strings using Quick3string algorithm.
     * @param a the array to be sorted
     * effects: puts a in sorted order. i.e. a[i] <= a[j]
     *           for all 0 <= i < j < a.length. 
     */
    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (lo >= hi) {  // base case
            return;
        }

        int lt = lo;
        int gt = hi;
        int i = lo + 1;
        int v = charAt(a[lo], d);
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) {
                exch(a, lt++, i++);
            } else if (t > v) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1, d);
        if (v > 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    private static int charAt(String s, int d) {
        if (d >= s.length()) {
            return 0;
        }
        return s.charAt(d) + 1;
    }
}
