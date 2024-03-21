package Algorithms;

public class SelectionSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        int min;
        for (int i = 0; i < a.length; i += 1) {
            /* 找到 a[i, a.length-1] 中的最小值的序号 */
            min = i;
            for (int j = i + 1; j < a.length; j += 1) {
                if (less(a[j], a[min])) min = j;
            }
            /* 将最小元素和 a[i] 交换 */
            exch(a, min, i);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}