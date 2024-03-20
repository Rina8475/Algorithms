package Algorithms;

public class BinarySearch {
    /** search - 在有序数组 a 中查找指定元素 v 所在的序号 */
    public static int search(Comparable[] a, Comparable v) {
        return search(a, v, 0, a.length - 1);
    }

    public static int search(Comparable[] a, Comparable v, int lo, int hi) {
        int mid;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if      (v.compareTo(a[mid]) < 0)   hi = mid - 1;
            else if (v.compareTo(a[mid]) > 0)   lo = mid + 1;
            else    return mid;
        }
        return -1;
    }

    /** search - 在有序数组 a 中查找指定元素 v 所在的序号 
     * 为了支持基本类型 int 而创建的函数 (Fuck java) */
    public static int search(int[] a, int v) {
        return search(a, v, 0, a.length - 1);
    }

    public static int search(int[] a, int v, int lo, int hi) {
        int mid;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if      (v < a[mid])   hi = mid - 1;
            else if (v > a[mid])   lo = mid + 1;
            else    return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        QuickSort.sort(a);
        System.out.println(Sort.arrayToString(a));
        for (Integer i : new Integer[] {12, 1, -1, 100}) {
            System.out.format("The index of elem %3d is %2d\n", i, search(a, i));
        }
    }
}
