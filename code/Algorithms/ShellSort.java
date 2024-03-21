package Algorithms;

public class ShellSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        int x = 1;
        // 构造一个增长序列
        while (x < a.length / 3)
            x = 3 * x + 1;
        // 按照不同区间进行插入排序
        while (x >= 1) {
            for (int i = 0; i < a.length; i += 1) {
                for (int j = i - x; j >= 0 && less(a[j+x], a[j]); j -= x)
                    exch(a, j, j + x);
            }
            x /= 3;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}
