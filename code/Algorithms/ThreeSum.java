package Algorithms;
import java.util.Arrays;

/* ThreeSum - 给定 N 个整数 求出所有的和为 0 的三元组的个数 */
public class ThreeSum {
    public static int count(int[] a) {
        int count = 0;
        Arrays.sort(a);
        for (int i = 0; i < a.length; i += 1) 
            for (int j = i + 1; j < a.length; j += 1)
                if (BinarySearch.search(a, -(a[i] + a[j])) >= 0)
                    count += 1;
        return count;
    }

    public static void main(String[] args) {
        int[] a = new int[] {30, -40, -20, -10, 40, 0, 10, 5};
        assert count(a) == 4;
    }
}
