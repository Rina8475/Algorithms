package DataStructure;
import java.util.Iterator;

/** ResizingArrayStack.java - 使用 Resizing Array 来实现栈 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] a;
    private int sz;

    public ResizingArrayStack() {
        a = (Item []) new Object[1];
        sz = 0;
    }

    private void resize(int N) {
        Item[] s = (Item []) new Object[N];
        for (int i = 0; i < sz; i += 1)
            s[i] = a[i];
        a = s;
    }

    public void push(Item item) {
        if (sz == a.length)
            resize(a.length * 2);
        a[sz] = item;
        sz += 1;
    }

    /* pop - 这里需要注意避免在 a.length = 1 的时候 resize
     * 否则会导致 a.length = 0 */
    public Item pop() {
        Item item;
        sz -= 1;
        item = a[sz];
        a[sz] = null;
        if (sz > 0 && sz == a.length / 4)
            resize(a.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < sz;
        }

        @Override
        public Item next() {
            return a[index++];
        }
    }

    public static void main(String[] args) {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        String[] str_set = new String[] {"to", "be", "or", "not", "to", "-", "be", 
            "-", "-", "that", "-", "-", "-", "is"};
        for (String s: str_set) {
            if (s.equals("-")) 
                System.out.println(stack.pop());
            else stack.push(s);
        }
        System.out.println(stack.size());
        for (String s: stack)
            System.out.println(s);
    }
}
