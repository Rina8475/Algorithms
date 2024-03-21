package Algorithms;

/* Sort - Implement some useful static method */
public class Sort {
    public static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i += 1) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    public static String arrayToString(Comparable[] a) {
        if (a == null)
            return "{}";
        return arrayToString(a, 0, a.length - 1);
    }

    public static String arrayToString(Comparable[] a, int lo, int hi) {
        String s = "{ " + a[lo].toString();
        for (int i = lo + 1; i <= hi; i += 1) {
            s += ", ";
            s += a[i].toString();
        }
        s += " }";
        return s;
    }
}
