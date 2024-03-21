package Algorithms;

public class MergeSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        T[] aux = (T[]) new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    /* Merge Sort 如果想要避免 merge 操作最开始的复制操作 可以简单地将此函数中
     * 对子数组进行排序的两行更改为
     *      sort(aux, a, lo, mid);
     *      sort(aux, a, mid + 1, hi); 
     * 另外还需要更改主函数 sort(T[] a) 为
     *      T[] aux = a.clone(); */
    private static <T extends Comparable<T>> void sort(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo)
            return;

        // 拆分数组 分别排序
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        // 将两个子数组通过 Merge 操作归并起来
        merge(a, aux, lo, hi, mid);
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
        System.out.println(isSorted(a));
    }
}
