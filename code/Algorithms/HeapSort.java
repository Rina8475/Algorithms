package Algorithms;

public class HeapSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        // 建堆
        for (int i = (a.length - 2) / 2; i >= 0; i -= 1)
            sink(a, i, a.length - 1);
        // 选择元素
        for (int i = a.length - 1; i > 0; i -= 1) {
            exch(a, 0, i);
            sink(a, 0, i - 1);
        }
    }

    /* sink - 将小元素下沉 */
    private static <T extends Comparable<T>> void sink(T[] a, int k, int N) {
        int j;

        while (2 * k + 1 <= N) {
            j = 2 * k + 1;
            if (j < N && less(a[j], a[j+1]))
                j += 1;
            if (!less(a[k], a[j]))
                break;
            exch(a, k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}
