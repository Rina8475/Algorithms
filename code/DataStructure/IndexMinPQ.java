package DataStructure;

import java.util.Arrays;

public class IndexMinPQ<Item extends Comparable<Item>> {
    private int n; // number of elements on priority queue
    private int[] pq; // priority queue of indices
    private int[] qp; // inverse of pq, qp[pq[i]] = pq[qp[i]] = i
    private Item[] items; // values associated with indices

    /* create a priority queue of size maxN */
    public IndexMinPQ(int maxN) {
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        items = (Item[]) new Comparable[maxN + 1];
        Arrays.fill(qp, -1);
    }

    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k /= 2;
        }
    }

    private void sink(int k) {
        int j;
        while (2 * k <= n) {
            j = 2 * k;
            if (j < n && less(j + 1, j)) {
                j += 1;
            }
            if (!less(j, k)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }

    /* accept index I and J, which are index of pq */
    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    /* insert a key-value pair (k, item) into the priority queue */
    public void insert(int k, Item item) {
        n += 1;
        pq[n] = k;
        qp[k] = n;
        items[k] = item;
        swim(n);
    }

    /* change the value of the key-value pair with the given key K */
    public void change(int k, Item item) {
        items[k] = item;
        swim(k);
        sink(k);
    }

    /* if the priority queue contains a key-value pair with the given key */
    public boolean contains(int k) {
        return qp[k] != -1;
    }

    /* delete the key-value pair with the given key from the priority queue */
    public void delete(int k) {
        int index = qp[k];
        exch(index, n);
        n -= 1;
        swim(index);
        sink(index);
        items[k] = null;
        qp[k] = -1;
    }

    /* return a minimal item */
    public Item min() {
        return items[pq[1]];
    }

    /* return a minimal item’s index */
    public int minIndex() {
        return pq[1];
    }

    /* remove a minimal item and return its index */
    public int delMin() {
        int index = pq[1];
        exch(1, n);
        n -= 1;
        sink(1);
        qp[index] = -1;
        items[index] = null;
        return index;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    /* number of items in the priority queue */
    public int size() {
        return n;
    }
}
