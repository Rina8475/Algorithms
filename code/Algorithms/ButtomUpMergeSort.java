package Algorithms;

public class ButtomUpMergeSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        T[] aux = (T[]) new Comparable[a.length];
        for (int sz = 1; sz < a.length; sz += sz) 
            for (int i = sz; i < a.length; i += 2 * sz) 
                merge(a, aux, i - sz, Math.min(i + sz - 1, a.length - 1), i - 1);
    }

    /* 执行 merge 操作 */
    private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int hi, int mid) {
        // 将 a[lo, hi] 复制到 aux[lo, hi] 中
        for (int k = lo; k <= hi; k += 1) 
            aux[k] = a[k];

        // 将 元素从 aux[lo, hi] 中取回到 a[lo, hi] 中
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k += 1) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        System.out.println(Sort.arrayToString(a));
        assert isSorted(a);
    }
}
