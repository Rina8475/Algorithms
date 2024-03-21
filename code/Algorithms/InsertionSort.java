package Algorithms;
public class InsertionSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        for (int i = 1; i < a.length; i += 1) {
            // 将元素 a[i] 插入有序数组 a[0, i-1] 中
            for (int j = i-1; j >= 0 && less(a[j+1], a[j]); j -= 1) {
                exch(a, j, j+1);
            } 
        }
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}
