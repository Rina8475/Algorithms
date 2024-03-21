package Algorithms;

public class ThreeWayQuickSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        sort(a, 0, a.length-1);
    }

    private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo)
            return;

        /* 假设以 a[lo] 作为分割元素 
         * a[lo, lt) 区间的元素小于 a[lo]
         * a[lt, i)  区间的元素等于 a[lo]
         * a[i, gt]  区间的元素未排序
         * a(gt, hi] 区间的元素大于 a[lo] */
        int lt = lo;
        int gt = hi;
        int i = lo + 1;
        T v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0)   exch(a, i++, lt++);
            else if (cmp > 0)   exch(a, i, gt--);
            else                i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);        
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}
