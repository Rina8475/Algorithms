package DataStructure;

public class MaxPQ<Item extends Comparable<Item>> {
    private Item[] items;
    private int end;

    public MaxPQ() {
        items = (Item[]) new Comparable[2];
        end = 1;        // items[0] is unused
    }

    public MaxPQ(Item[] a) {
        end = a.length + 1;
        items = (Item[]) new Comparable[end];
        for (int i = 0; i < a.length; i += 1)
            items[i + 1] = a[i];
        // sink from buttom to up
        for (int i = (end - 1) / 2; i > 0; i -= 1)
            sink(i);
    }

    /* exch - 交换 items 数组里的第 i 项和第 j 项 */
    private void exch(int i, int j) {
        Item item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    private void resize(int N) {
        Item[] copy = (Item[]) new Comparable[N];

        for (int i = 1; i < end; i += 1)
            copy[i] = items[i];
        items = copy;
    }

    private void swim(int i) {
        while (i > 1 && items[i/2].compareTo(items[i]) < 0) {
            exch(i, i/2);
            i /= 2;
        }
    }

    private void sink(int i) {
        int j;
        while (2 * i < end) {
            j = 2 * i;
            if (j + 1 < end && items[j].compareTo(items[j + 1]) < 0)
                j += 1;
            if (items[i].compareTo(items[j]) >= 0)
                break;
            exch(i, j);
            i = j;
        }
    }

    public void insert(Item v) {
        if (end == items.length)
            resize(items.length * 2);
        items[end] = v;
        swim(end);
        end += 1;
    }

    public Item delMax() {
        Item item = items[1];
        end -= 1;
        items[1] = items[end];
        items[end] = null;
        sink(1);
        if (end == items.length / 4)
            resize(items.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return end == 1;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> pq;
        
        // test 1
        pq = new MaxPQ<Integer>();
        Integer[] a = {2, 1, 3, 15, 26, 12, 44};
        for (Integer i: a)
            pq.insert(i);
        while (!pq.isEmpty())
            System.out.println(pq.delMax());

        // test 2
        pq = new MaxPQ<Integer>(a);
        while (!pq.isEmpty())
            System.out.println(pq.delMax());
    }
}