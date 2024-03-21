package Algorithms;

public class QuickSelection {
    /* select - 选择数组 a 中第 k 小的元素 
     * P.S. 最小元素对应 k = 0 */
    private static <T extends Comparable<T>> T select(T[] a, int k) {
        int lo = 0;
        int hi = a.length - 1;
        int mid;

        while (lo <= hi) {
            mid = QuickSort.partition(a, lo, hi);
            if      (k < mid)   hi = mid - 1;
            else if (k > mid)   lo = mid + 1;
            else                return a[k];
        }
        return a[k];        // 虽然我觉得应该返回 null 但源码是这样写的
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        assert (select(a, 3) == 12);
    }
}