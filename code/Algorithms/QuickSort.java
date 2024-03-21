package Algorithms;

public class QuickSort extends Sort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        // TODO 此处应该进行数组打乱操作 
        sort(a, 0, a.length - 1);
    }

    /* sort - 将 a[lo, hi] 排序为有序 
     * P.S. 教材上的版本和这个略有不同 */
    private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo) 
            return;

        // 进行 partition 操作
        int j = partition(a, lo, hi);
        // 分别对两个子数组进行排序
        sort(a, lo, j - 1);   
        sort(a, j + 1, hi);
        assert isSorted(a, lo, hi) : arrayToString(a, lo, hi);
    }

    /* partition - 对 a[lo, hi] 进行分割操作
     * 先选取一个分割元素 a[j], 然后输出数组保证 
     * a[lo, j-1] 内的元素都小于等于 a[j]
     * a[j+1, hi] 内的元素都大于等于 a[j]
     * 最后返回分割元素的序号 j                    
     * P.S. 这里不加 private 是为了方便类 QuickSelection 调用 */
    static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
        int i = lo + 1, j = hi;
        while (true) {
            while (i <= hi && !less(a[lo], a[i]))       // while a[i] <= a[lo]
                i += 1;
            while (j > lo && !less(a[j], a[lo]))        // while a[j] >= a[lo]
                j -= 1;
            if (j < i)  break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        sort(a);
        assert isSorted(a);
    }
}
