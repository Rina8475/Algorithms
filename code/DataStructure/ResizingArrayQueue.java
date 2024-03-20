package DataStructure;
import java.util.Iterator;

/** ResizingArrayQueue.java - 使用 Resizing Array 来实现队列 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int start;
    private int end;
    private int sz;
    
    public ResizingArrayQueue() {
        a = (Item[]) new Object[1];
        sz = start = end = 0;
    }

    /** resize - 将原数组映射为大小为 N 的新数组 并将原数组
     * 内的元素复制到新数组中
     * P.S. 在复制的时候需要将原数组的队尾元素复制到 index 为 0 的位置
     * 其余元素依次复制 */
    private void resize(int N) {
        Item[] copy = (Item[]) new Object[N];
        assert N >= sz;

        for (int i = 0; i < sz; i += 1)
            copy[i] = a[(i + start) % a.length];
        start = 0;
        end = sz;
        a = copy;
    }

    /** enqueue - 入队列 当检测到队列满时需要 resize */
    public void enqueue(Item item) {
        if (sz == a.length)
            resize(a.length * 2);
        a[end] = item;
        end = (end + 1) % a.length;
        sz += 1;
    }

    /** dequeue - 出队列 当检测到队列元素少于 1/4 时 resize
     * P.S. 为了避免队列长度 resize 为 0 当队列长度为 1 的时候
     * 这个函数就不能 resize */
    public Item dequeue() {
        Item result = a[start];
        a[start] = null;
        start = (start + 1) % a.length;
        sz -= 1;
        if (sz > 0 && sz == a.length / 4)
            resize(a.length / 2);
        return result; 
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int size = sz;
        private int idx = start;

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            Item result = a[idx];
            idx = (idx + 1) % a.length;
            size -= 1;
            return result;
        }
    }

    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        String[] str_set = new String[] {"to", "be", "or", "not", "to", "-", "be", 
            "-", "-", "that", "-", "-", "-", "is"};
        for (String s: str_set) {
            if (s.equals("-")) 
                System.out.println(queue.dequeue());
            else queue.enqueue(s);
        }
        System.out.println(queue.size());
        for (String s: queue)
            System.out.println(s);
    }
}
